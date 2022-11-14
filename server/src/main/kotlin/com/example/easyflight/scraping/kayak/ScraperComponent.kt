package com.example.easyflight.scraping.kayak

import com.example.easyflight.airports.service.AirportInterface
import com.example.easyflight.flights.adapters.Flight
import com.example.easyflight.flights.adapters.Offer
import com.example.easyflight.flights.adapters.Scale
import com.example.easyflight.flights.adapters.response.FlightSearchResponse
import com.example.easyflight.scraping.enum.HtmlAttributes
import com.example.easyflight.scraping.enum.HtmlTags
import com.example.easyflight.flights.exceptions.KayakScraperException
import com.example.easyflight.scraping.GenericSearchFlow
import com.example.easyflight.scraping.util.UrlBuilder
import com.example.easyflight.scraping.drivers.chrome.ChromeDriverInitializer
import com.example.easyflight.scraping.drivers.edge.EdgeDriverInitializer
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class ScraperComponent(
    edgeDriverInitializer: EdgeDriverInitializer,
    urlBuilder: UrlBuilder,
    private val airportInterface: AirportInterface
) : GenericSearchFlow(edgeDriverInitializer, urlBuilder) {

    @Value("\${cookies.close.popup}")
    private lateinit var closePopUp: String

    @Value("\${loading.flights.ended}")
    private lateinit var loadingFlightsEnded: String

    @Value("\${departure.time}")
    private lateinit var departTimeClass: String

    @Value("\${arrival.time}")
    private lateinit var arrivalTimeClass: String

    @Value("\${airport.name}")
    private lateinit var airportNameClass: String

    @Value("\${scales.number}")
    private lateinit var scalesNumberClass: String

    @Value("\${scales.information}")
    private lateinit var scalesInfoClass: String

    @Value("\${airlines.names}")
    private lateinit var airlinesNamesClass: String

    @Value("\${flight.result}")
    private lateinit var flightResultClass: String

    @Value("\${price.text}")
    private lateinit var priceTextClass: String

    @Value("\${provider.name}")
    private lateinit var providerNameClass: String

    @Value("\${best.offer.url}")
    private lateinit var bestOfferUrlClass: String

    @Value("\${kayak.base.url}")
    private lateinit var kayaBaseUrl: String

    private val logger: Logger = LoggerFactory.getLogger(ScraperComponent::class.java)

    override fun prepareScreenForScraping() {
        //CLOSE COOKIES POP-UP
        WebDriverWait(driver, 10)
            .ignoring(StaleElementReferenceException::class.java)
            .until(ExpectedConditions.elementToBeClickable(By.className(closePopUp)))
            .click()

        //WAIT TILL SEARCH COMPLETED
        logger.info("Waiting for the search to finish")
        WebDriverWait(driver, 50)
            .until(
                ExpectedConditions.attributeToBe(
                    driver.findElement(By.className(loadingFlightsEnded)).findElement(By.tagName(HtmlTags.DIV.tag)),
                    HtmlAttributes.ARIA_BUSY.attr,
                    "false"
                )
            )

        //SHOW MORE RESULTS
        //TODO(load more results)

    }

    override fun extractFlights(document: Document, destination: String): List<FlightSearchResponse> {
        try {

            val travelOfferList = mutableListOf<FlightSearchResponse>()
            document.getElementsByClass(flightResultClass).forEach { element ->

                val departTimes = element.getElementsByClass(departTimeClass)
                val arrivalTimes = element.getElementsByClass(arrivalTimeClass)
                val airportNames = element.getElementsByClass(airportNameClass)
                val scalesNumbers = element.getElementsByClass(scalesNumberClass)

                if (departTimes.size >= 2 && arrivalTimes.size >= 2 && airportNames.size >= 4 && scalesNumbers.size >= 2) {

                    val scalesMap = getScales(
                        numOutboundScales = if (scalesNumbers[0].text().contains(" ")) scalesNumbers[0].text()
                            .split(" ")[0].toInt()
                        else 0,
                        numReturnScales = if (scalesNumbers[1].text().contains(" ")) scalesNumbers[1].text()
                            .split(" ")[0].toInt()
                        else 0,
                        scalesElement = element.getElementsByClass(scalesInfoClass)
                    )

                    travelOfferList.add(
                        FlightSearchResponse(
                            outboundFlight = Flight(
                                departureTime = departTimes[0].text(),
                                departureAirport = airportInterface.getAirportName(
                                    airportNames[0].text().substring(0, 3)
                                ),
                                arrivalTime = arrivalTimes[0].text(),
                                arrivalAirport = airportInterface.getAirportName(airportNames[1].text().substring(0, 3)),
                                scales = scalesMap.filter { it.value == 1 }.keys.toList()
                            ),
                            returnFlight = Flight(
                                departureTime = departTimes[1].text(),
                                departureAirport = airportInterface.getAirportName(
                                    airportNames[2].text().substring(0, 3)
                                ),
                                arrivalTime = arrivalTimes[1].text(),
                                arrivalAirport = airportInterface.getAirportName(airportNames[3].text().substring(0, 3)),
                                scales = scalesMap.filter { it.value == 2 }.keys.toList()
                            ),
                            offer = getOffer(element),
                            airlineNames = element.getElementsByClass(airlinesNamesClass).text().split(", ")
                        )
                    )

                } else throw KayakScraperException("Error getting main data")

            }
            return travelOfferList
        } catch (ex: Exception) {
            throw KayakScraperException(ex.message!!)
        }

    }


    private fun getScales(numOutboundScales: Int, numReturnScales: Int, scalesElement: Elements): Map<Scale, Int> {
        try {
            val scalesMap = mutableMapOf<Scale, Int>()
            var index = 1

            scalesElement.subList(0, numOutboundScales + numReturnScales).forEach { element ->
                if (scalesElement.indexOf(element) == numOutboundScales) index = 2

                scalesMap[Scale(
                    airportName = airportInterface.getAirportName(element.text()),
                    waitTime = element.attr(HtmlAttributes.TITLE.attr).substringAfter("de ").substringBefore(" en")
                )] = index
            }
            return scalesMap
        } catch (ex: Exception) {
            throw KayakScraperException("Error while extracting scales: ${ex.message}")
        }
    }

    private fun getOffer(element: Element): Offer {
        return try {

            Offer(
                price = element.getElementsByClass(priceTextClass)[0].text().substringBefore(' ')
                    .toInt(),
                provider = if (element.getElementsByClass(providerNameClass).isEmpty()) ""
                else element.getElementsByClass(providerNameClass)[0].text(),
                url = kayaBaseUrl.plus(element.getElementsByClass(bestOfferUrlClass)[0].getElementsByTag(HtmlTags.A.tag)[0].attr(HtmlAttributes.HREF.attr))
            )

//            //EXTRA OFFERS
//          val extraInfoClassRegex = "[class~=-extra-info-]"
//          val providerNameClassRegex = "[class~=providerName option-text]"
//            element.select(extraInfoClassRegex).forEach {
//                listOffer.add(
//                    Offer(
//                        price = it.getElementsByClass(priceTextClassName)[0].text().substringBefore('&')
//                            .toInt(),
//                        provider = it.select(providerNameClassRegex)[0].text(),
//                        url = "kayak.es".plus(it.getElementsByTag("a")[0].attr("href"))
//                    )
//                )
//            }
        } catch (ex: Exception) {
            throw KayakScraperException("Error while extracting offers: ${ex.message}")
        }

    }
}




