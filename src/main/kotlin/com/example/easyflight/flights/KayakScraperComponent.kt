package com.example.easyflight.flights

import com.example.easyflight.airports.repository.AirportRepository
import com.example.easyflight.flights.adapters.Flight
import com.example.easyflight.flights.adapters.Scale
import com.example.easyflight.flights.adapters.TimeLocationContainer
import com.example.easyflight.flights.adapters.TravelOffer
import com.example.easyflight.flights.exceptions.ScraperDataParsingException
import com.example.easyflight.flights.util.UrlBuilder
import com.example.easyflight.flights.util.drivers.edge.EdgeDriverInitializer
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class KayakScraperComponent(
    edgeDriverInitializer: EdgeDriverInitializer,
    urlBuilder: UrlBuilder,
    private val airportRepository: AirportRepository
) : GenericSearchFlow(edgeDriverInitializer, urlBuilder) {

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
//        LOGGER.info("Expanding result containers")
//        driver.findElementsByClassName("resultInner")
//            .apply { removeAt(0) }
//            .forEach {
//                WebDriverWait(driver, 2)
//                    .ignoring(ElementClickInterceptedException::class.java)
//                    .until(ExpectedConditions.elementToBeClickable(it)).click()
//            }
    }

    private fun getScales(outboundScales: Int, returnScales: Int, scalesElement: Elements ): Map<Scale, Int> {
        val scalesMap = mutableMapOf<Scale, Int>()
        if (outboundScales + returnScales == scalesElement.size) {
            scalesElement.forEach {
                val airportOpt = airportRepository.findById(it.text())
                if(airportOpt.isPresent){
                    val scale = Scale(
                        airportOpt.get(),
                        it.select("title").text().substringAfter("de ").substringBefore(" en"))
                }

            }

        } else {
            throw ScraperDataParsingException("The number of scales is not equal to the size of scales information element. KAYAK")
        }

        return scalesList
    }

    override fun extractFlights(document: Document, destination: String): List<TravelOffer> {
        try {
            val departTimeClassRegex = "[class~=depart-time]"
            val arrivalTimeClassRegex = "[class~=arrival-time]"
            val airportNameClassRegex = "[class~=airport-name]"
            val stopsTextClassRegex = "[class~=stops-text]"
            val stopsInfoClassRegex = "[class~=js-layover]"
            val priceTextClassRegex = "[class~=price-text]"
            val airlineNamesClassRegex = "[class~=codeshares-airline-names]"

            val locationClassName = "[class~=-station]"
            val travelOfferList = mutableListOf<TravelOffer>()
            document.getElementsByClass("Base-Results-HorizonResult").forEach { element ->

                val listInfos = mutableListOf<TimeLocationContainer>()
                val departTimes = element.select(departTimeClassRegex)
                val arrivalTimes = element.select(arrivalTimeClassRegex)
                val airportNames = element.select(airportNameClassRegex)
                val stopsText = element.select(stopsTextClassRegex)
                val stopsInfo = element.select(stopsInfoClassRegex)
                val priceText = element.select(priceTextClassRegex)
                val airlineNames = element.select(airlineNamesClassRegex)

                if (stopsText.size == 2) {
                    val numEscalasIda = stopsText[0].text().split(" ")[0].toInt()
                    val numEscalasVuelta = stopsText[1].text().split(" ")[0].toInt()
                    if (numEscalasIda + numEscalasVuelta == stopsInfo.size) {
                        stopsInfo.forEach {
                            val airportOpt = airportRepository.findById(it.text())
                            if(airportOpt.isPresent){
                                val scale = Scale(
                                    airportOpt.get(),
                                    it.select("title").text().substringAfter("de ").substringBefore(" en"))
                            }

                        }

                    }
                }

                if (departTimes.size == 2 && arrivalTimes.size == 2 && airportNames.size == 4 && stopsText.size == 2) {
                    travelOfferList.add(
                        TravelOffer(
                            outboundFlight = Flight(
                                departTimes[0].text(),
                                airportNames[0].text(),
                                arrivalTimes[0].text(),
                                airportNames[1].text()
                            ),
                            returnFlight = Flight(
                                departTimes[1].text(),
                                airportNames[2].text(),
                                arrivalTimes[1].text(),
                                airportNames[3].text()
                            )
                        )
                    )

                }

//                if (times.size == locations.size) {
//                    for (i in times.indices) {
//                        listInfos.add(TimeLocationContainer(times[i].text(), locations[i].text()))
//                    }
//                }
//
//                if (listInfos.size % 2 == 0) {
//                    val listFlights = mutableListOf<Flight>()
//                    for (i in 0 until listInfos.size - 1 step (2)) {
//                        listFlights.add(
//                            Flight(
//                                listInfos[i].time,
//                                listInfos[i + 1].time,
//                                listInfos[i].location,
//                                listInfos[i + 1].location
//                            )
//                        )
//                    }
//
//                    listFlights.forEach { flight ->
//                        if (flight.arrivalAirport
//                                .substringAfterLast('(')
//                                .substringBeforeLast(')') == destination
//                        ) {
//                            val index = listFlights.indexOf(flight)
//                            searchResponseList.add(
//                                SearchResponse(
//                                    listFlights.subList(0, index + 1),
//                                    listFlights.subList(index + 1, listFlights.size)
//                                )
//                            )
//                        }
//                    }
//                }
            }


            return travelOfferList
        } catch (ex: Exception) {
            LOGGER.error(ex.message)
            return listOf()
        }

    }
}




