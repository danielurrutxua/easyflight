package com.example.easyflight.flights

import com.example.easyflight.flights.adapters.Flight
import com.example.easyflight.flights.adapters.G16kContainer
import com.example.easyflight.flights.adapters.TravelOffer
import com.example.easyflight.flights.util.ChromeDriverInitializer
import com.example.easyflight.flights.util.UrlBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
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

    override fun extractFlights(document: Document, destination: String): List<TravelOffer> {
        try {
            var infoContainerClassName = "g16k"
            val timeClassName = "[class~=-time]"
            val locationClassName = "[class~=-station]"
            val listOffers = mutableListOf<TravelOffer>()
            document.getElementsByClass("Base-Results-HorizonResult")
                .apply { removeAt(0) }.forEach { element ->

                    val listInfos = mutableListOf<G16kContainer>()

                    val horas = element.select(timeClassName).filter { it.className().length == 9 }
//                    if (element.getElementsByClass(infoContainerClassName).isEmpty()) {
//                        infoContainerClassName = "[class~=-segment-info]"
//
//                        if(element.select(infoContainerClassName).isNotEmpty()){
//                            element.select(infoContainerClassName).forEach {
//
//                                val departureRow = it.getElementsByClass("dErF-departure-row")[0]
//                                val arrivalRow = it.getElementsByClass("dErF-arrival-row")[0]
//
//                                listInfos.add(
//                                    G16kContainer(
//                                        departureRow.getElementsByClass(timeClassName).text(),
//                                        departureRow.getElementsByClass(locationClassName).text()
//                                    )
//                                )
//
//                                listInfos.add(
//                                    G16kContainer(
//                                        arrivalRow.getElementsByClass(timeClassName).text(),
//                                        arrivalRow.getElementsByClass(locationClassName).text()
//                                    )
//                                )
//
//                            }
//                        }
//
//                    } else {
////                        element.getElementsByClass(infoContainerClassName).forEach {
////                            listInfos.add(
////                                G16kContainer(
////                                    it.getElementsByClass(timeClassName).text(),
////                                    it.getElementsByClass(locationClassName).text()
////                                )
////                            )
////                        }
//                    }


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
                                        TravelOffer(
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




