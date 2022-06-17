package com.example.easyflight.flights

import com.example.easyflight.flights.adapters.Flight
import com.example.easyflight.flights.adapters.TimeLocationContainer
import com.example.easyflight.flights.adapters.SearchResponse
import com.example.easyflight.flights.util.ChromeDriverInitializer
import com.example.easyflight.flights.util.UrlBuilder
import org.jsoup.nodes.Document
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class KayakScraperComponent(
    chromeDriverInitializer: ChromeDriverInitializer, urlBuilder: UrlBuilder
) : GenericSearchFlow(chromeDriverInitializer, urlBuilder) {

    private val LOGGER: Logger = LoggerFactory.getLogger(KayakScraperComponent::class.java)

    override fun prepareScreenForScraping() {
        //CLOSE COOKIES POP-UP
        WebDriverWait(driver, 10)
            .ignoring(StaleElementReferenceException::class.java)
            .until(ExpectedConditions.elementToBeClickable(By.className("dDYU-close")))
            .click()

        //WAIT TILL SEARCH COMPLETED
        LOGGER.info("Waiting for the search to finish")
        WebDriverWait(driver, 50)
            .until(
                ExpectedConditions.attributeToBe(
                    driver.findElement(By.className("col-advice")).findElement(By.tagName("div")),
                    "aria-busy",
                    "false"
                )
            )

        //SHOW MORE RESULTS
        //TODO(load more results)

        //EXPAND RESULTS CONTAINERS
        LOGGER.info("Expanding result containers")
        driver.findElementsByClassName("resultInner")
            .apply { removeAt(0) }
            .forEach {
                WebDriverWait(driver, 2)
                    .ignoring(ElementClickInterceptedException::class.java)
                    .until(ExpectedConditions.elementToBeClickable(it)).click()
            }
    }

    override fun extractFlights(document: Document, destination: String): List<SearchResponse> {
        try {
            val timeClassName = "[class~=-time]"
            val locationClassName = "[class~=-station]"
            val listOffers = mutableListOf<SearchResponse>()
            document.getElementsByClass("Base-Results-HorizonResult")
                .apply { removeAt(0) }.forEach { element ->

                    val listInfos = mutableListOf<TimeLocationContainer>()
                    val times = element.select(timeClassName).filter { it.className().length == 9 }
                    val locations = element.select(locationClassName).filter { it.className().length == 11 }

                    if (times.size == locations.size) {
                        for (i in 0..times.size) {
                            listInfos.add(TimeLocationContainer(times[i].text(), locations[i].text()))
                        }
                    }

                    if (listInfos.size % 2 == 0) {
                        val listFlights = mutableListOf<Flight>()
                        for (i in 0 until listInfos.size - 1 step (2)) {
                            listFlights.add(
                                Flight(
                                    listInfos[i].time,
                                    listInfos[i + 1].time,
                                    listInfos[i].location,
                                    listInfos[i + 1].location
                                )
                            )
                        }

                        listFlights.forEach { flight ->
                            if (flight.arrivalAirport
                                    .substringAfterLast('(')
                                    .substringBeforeLast(')') == destination
                            ) {
                                val index = listFlights.indexOf(flight)
                                listOffers.add(
                                    SearchResponse(
                                        listFlights.subList(0, index + 1),
                                        listFlights.subList(index + 1, listFlights.size)
                                    )
                                )
                            }
                        }
                    }
                }


            return listOffers
        } catch (ex: Exception) {
            LOGGER.error(ex.message)
            return listOf()
        }

    }
}




