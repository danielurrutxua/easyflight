package com.example.easyflight.flights

import com.example.easyflight.airports.service.AirportService
import com.example.easyflight.flights.adapters.Flight
import com.example.easyflight.flights.adapters.FlightResponse
import com.example.easyflight.flights.adapters.Offer
import com.example.easyflight.flights.adapters.Scale
import com.example.easyflight.flights.util.UrlBuilder
import com.example.easyflight.flights.util.drivers.chrome.ChromeDriverInitializer
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
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
    edgeDriverInitializer: ChromeDriverInitializer,
    urlBuilder: UrlBuilder,
    private val airportService: AirportService
) : GenericSearchFlow(edgeDriverInitializer, urlBuilder) {

    private val logger: Logger = LoggerFactory.getLogger(KayakScraperComponent::class.java)

    override fun prepareScreenForScraping() {
        //CLOSE COOKIES POP-UP
        WebDriverWait(driver, 10)
            .ignoring(StaleElementReferenceException::class.java)
            .until(ExpectedConditions.elementToBeClickable(By.className("dDYU-close")))
            .click()

        //WAIT TILL SEARCH COMPLETED
        logger.info("Waiting for the search to finish")
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

    }

    override fun extractFlights(document: Document, destination: String): List<FlightResponse> {
        try {


            val departTimeClassRegex = "[class~=depart-time]"
            val arrivalTimeClassRegex = "[class~=arrival-time]"
            val airportNameClassRegex = "[class~=airport-name]"
            val stopsTextClassRegex = "[class~=stops-text]"
            val stopsInfoClassName = "js-layover"
            val airlineNamesClassRegex = "[class~=codeshares-airline-names]"

            val travelOfferList = mutableListOf<FlightResponse>()
            document.getElementsByClass("Base-Results-HorizonResult").forEach { element ->

                val departTimes = element.select(departTimeClassRegex)
                val arrivalTimes = element.select(arrivalTimeClassRegex)
                val airportNames = element.select(airportNameClassRegex)
                val stopsText = element.select(stopsTextClassRegex)
                val stopsInfo = element.getElementsByClass(stopsInfoClassName)
                val airlineNames = element.select(airlineNamesClassRegex)


                if (departTimes.size >= 2 && arrivalTimes.size >= 2 && airportNames.size >= 4 && stopsText.size >= 2) {

                    val scalesMap = getScales(
                        numOutboundScales = if (stopsText[0].text().contains(" ")) stopsText[0].text()
                            .split(" ")[0].toInt()
                        else 0,
                        numReturnScales = if (stopsText[1].text().contains(" ")) stopsText[1].text()
                            .split(" ")[0].toInt()
                        else 0,
                        scalesElement = stopsInfo
                    )

                    val offers = getOffers(element)

                    travelOfferList.add(
                        FlightResponse(
                            outboundFlight = Flight(
                                departureTime = departTimes[0].text(),
                                departureAirport = airportService.getAirportName(
                                    airportNames[0].text().substring(0, 3)
                                ),
                                arrivalTime = arrivalTimes[0].text(),
                                arrivalAirport = airportService.getAirportName(airportNames[1].text().substring(0, 3)),
                                scales = scalesMap.filter { it.value == 1 }.keys.toList()
                            ),
                            returnFlight = Flight(
                                departureTime = departTimes[1].text(),
                                departureAirport = airportService.getAirportName(
                                    airportNames[2].text().substring(0, 3)
                                ),
                                arrivalTime = arrivalTimes[1].text(),
                                arrivalAirport = airportService.getAirportName(airportNames[3].text().substring(0, 3)),
                                scales = scalesMap.filter { it.value == 2 }.keys.toList()
                            ),
                            offers = offers,
                            airlineNames = airlineNames.text().split(", ")
                        )
                    )

                }

            }

            return travelOfferList
        } catch (ex: Exception) {
            logger.error(ex.message)
            return listOf()
        }

    }


    private fun getScales(numOutboundScales: Int, numReturnScales: Int, scalesElement: Elements): Map<Scale, Int> {
        val scalesMap = mutableMapOf<Scale, Int>()
        var index = 1

        scalesElement.subList(0, numOutboundScales + numReturnScales).forEach { element ->
            if (scalesElement.indexOf(element) == numOutboundScales) index = 2

            scalesMap[Scale(
                airportName = airportService.getAirportName(element.text()),
                waitTime = element.attr("title").substringAfter("de ").substringBefore(" en")
            )] = index

        }
        return scalesMap
    }

    private fun getOffers(element: Element): List<Offer> {
        val listOffer = mutableListOf<Offer>()
        val mainOfferResultClassName = "Flights-Results-FlightPriceSection"
        val providerNameClassName = "name-only-text"
        val extraInfoClassRegex = "[class~=-extra-info-]"
        val priceTextClassName = "price-text"
        val providerNameClassRegex = "[class~=providerName option-text]"
        //MAIN OFFER
        val mainOffer = element.getElementsByClass(mainOfferResultClassName).last()!!

        listOffer.add(
            Offer(
                price = mainOffer.getElementsByClass(priceTextClassName)[0].text().substringBefore(' ').toInt(),
                provider = if (mainOffer.getElementsByClass(providerNameClassName).isEmpty()) ""
                else mainOffer.getElementsByClass(providerNameClassName)[0].text(),
                url = "kayak.es".plus(mainOffer.getElementsByTag("a")[0].attr("href"))
            )
        )

        //EXTRA OFFERS
        element.select(extraInfoClassRegex).forEach {
            listOffer.add(
                Offer(
                    price = it.getElementsByClass(priceTextClassName)[0].text().substringBefore('&').toInt(),
                    provider = it.select(providerNameClassRegex)[0].text(),
                    url = "kayak.es".plus(it.getElementsByTag("a")[0].attr("href"))
                )
            )
        }
        return listOffer

    }
}




