package com.example.easyflight.scraping.kayak

import Airline
import Airport
import com.example.easyflight.airports.service.AirportInterface
import com.example.easyflight.flights.adapters.*
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.FlightSearchResponse
import com.example.easyflight.flights.exceptions.KayakScraperException
import com.example.easyflight.scraping.GenericSearchFlow
import com.example.easyflight.scraping.drivers.edge.EdgeDriverInitializer
import com.example.easyflight.scraping.enum.HtmlAttributes
import com.example.easyflight.scraping.enum.HtmlTags
import com.example.easyflight.scraping.util.UrlBuilder
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.jsoup.Jsoup
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
import java.util.regex.Pattern


@Component
class ScraperComponent(
        edgeDriverInitializer: EdgeDriverInitializer,
        urlBuilder: UrlBuilder,
        private val airportInterface: AirportInterface
) : GenericSearchFlow(edgeDriverInitializer, urlBuilder) {

    private val LOGGER = LoggerFactory.getLogger(ScraperComponent::class.java)

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
        val element = driver.findElementByClassName(closePopUp).click()
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

    override fun extractFlights(document: Document, request: FlightSearchRequest): List<FlightSearchResponse> =
            if (request.arrivalDate.isBlank()) getFlightsWithoutReturn(document) else getFlightsWithReturn(document)

    private fun getFlightsWithoutReturn(document: Document): List<FlightSearchResponse> {
        val travelOfferList = mutableListOf<FlightSearchResponse>()
        document.getElementsByAttribute("data-resultid").forEach { element ->
            try {

                val departTimes = element.getElementsByClass(departTimeClass)
                val arrivalTimes = element.getElementsByClass(arrivalTimeClass)
                val airportNames = element.getElementsByClass(airportNameClass)
                val scalesNumbers = element.getElementsByClass(scalesNumberClass)


                val scalesMap = getScales(
                        numOutboundScales = if (scalesNumbers[0].text().contains(" ")) scalesNumbers[0].text()
                                .split(" ")[0].toInt()
                        else 0,
                        scalesElement = element.getElementsByClass(scalesInfoClass)
                )

                travelOfferList.add(
                        FlightSearchResponse(
                                outboundFlight = Flight1(
                                        departureTime = departTimes[0].text(),
                                        departureAirport = airportInterface.getAirportName(
                                                airportNames[0].text().substring(0, 3)
                                        ),
                                        arrivalTime = arrivalTimes[0].text(),
                                        arrivalAirport = airportInterface.getAirportName(
                                                airportNames[1].text().substring(0, 3)
                                        ),
                                        scales = scalesMap.keys.toList()
                                ),
                                returnFlight = null,
                                offer = getOffer(element),
                                airlineNames = element.getElementsByClass(airlinesNamesClass).text().split(", ")
                        )
                )

            } catch (ex: Exception) {
                LOGGER.error(ex.message)
            }
        }
        return travelOfferList
    }

    private fun getFlightsWithReturn(document: Document): List<FlightSearchResponse> {
        val travelOfferList = mutableListOf<FlightSearchResponse>()
        document.getElementsByAttribute("data-resultid").forEach { element ->

            try {
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
                                    outboundFlight = Flight1(
                                            departureTime = departTimes[0].text(),
                                            departureAirport = airportInterface.getAirportName(
                                                    airportNames[0].text().substring(0, 3)
                                            ),
                                            arrivalTime = arrivalTimes[0].text(),
                                            arrivalAirport = airportInterface.getAirportName(
                                                    airportNames[1].text().substring(0, 3)
                                            ),
                                            scales = scalesMap.filter { it.value == 1 }.keys.toList()
                                    ),
                                    returnFlight = Flight1(
                                            departureTime = departTimes[1].text(),
                                            departureAirport = airportInterface.getAirportName(
                                                    airportNames[2].text().substring(0, 3)
                                            ),
                                            arrivalTime = arrivalTimes[1].text(),
                                            arrivalAirport = airportInterface.getAirportName(
                                                    airportNames[3].text().substring(0, 3)
                                            ),
                                            scales = scalesMap.filter { it.value == 2 }.keys.toList()
                                    ),
                                    offer = getOffer(element),
                                    airlineNames = element.getElementsByClass(airlinesNamesClass).text().split(", ")
                            )
                    )

                } else throw KayakScraperException("Error getting main data")

            } catch (ex: Exception) {
                LOGGER.error(ex.message)
            }

        }
        return travelOfferList
    }


    private fun getScales(numOutboundScales: Int, numReturnScales: Int = 0, scalesElement: Elements): Map<Layover, Int> {
        try {
            val scalesMap = mutableMapOf<Layover, Int>()
            var index = 1

            scalesElement.subList(0, numOutboundScales + numReturnScales).forEach { element ->
                if (scalesElement.indexOf(element) == numOutboundScales) index = 2

                scalesMap[Layover(
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
                    url = kayaBaseUrl.plus(
                            element.getElementsByClass(bestOfferUrlClass)[0].getElementsByTag(HtmlTags.A.tag)[0].attr(
                                    HtmlAttributes.HREF.attr
                            )
                    )
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


    override fun getKayakFLights(url: String) {
        val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
        val response = Jsoup.connect(url).header("User-Agent", userAgent).execute()

        val document = response.parse()
        val scripts = document.select("script")

// Buscar el último script que sea de tipo "text/javascript"
        val script = scripts.last { it.attr("type").equals("text/javascript", ignoreCase = true) }


        extractData(script)


    }

    private fun extractData(script: Element) {
        // Obtiene el contenido del script
        val scriptContent = script.data()

        // Define un patrón para extraer la sección del JSON que necesitas
        val pattern = Pattern.compile("FlightResultsList\":(.*?),\"FlightFilterData")
        val matcher = pattern.matcher(scriptContent)

        // Verifica si el patrón coincide con el contenido del script
        if (matcher.find()) {
            // Extrae la sección del JSON que necesitas
            val json = matcher.group(1)
            // Convierte el string JSON en un objeto JSON en Kotlin
            val resultsJson = Gson().fromJson(json, JsonObject::class.java).getAsJsonObject("results")

            resultsJson.keySet()
                    .filter { key -> key.matches(Regex("[a-zA-Z0-9]{32}")) }
                    .toList()
                    .forEach { key -> getResult(resultsJson.getAsJsonObject(key)) }


            // Imprime la sección del JSON que necesitas
            println(json)
        } else {
            println("No se encontró la sección del JSON que necesitas.")
        }
    }

    private fun getResult(resultJson: JsonObject): Result {
        val resultId = resultJson.get("resultId").asString
        val legs = resultJson.getAsJsonArray("legs").map { legJson ->
            val legId = legJson.asJsonObject.get("legId").asString
            val duration = legJson.asJsonObject.get("legDurationDisplay").asString
            val flights = legJson.asJsonObject.getAsJsonArray("segments").map { segmentJson ->
                val number = segmentJson.asJsonObject.get("flightNumber").asInt
                val airline = segmentJson.asJsonObject.get("airline").asJsonObject.let { airlineJson ->
                    Airline(
                            code = airlineJson.get("code").asString,
                            name = airlineJson.get("name").asString,
                            logoUrl = airlineJson.get("logoUrl").asString
                    )
                }
                val departure = segmentJson.asJsonObject.get("departure").asJsonObject.let { departureJson ->
                    val localDateTime = departureJson.get("isoDateTimeLocal").asString
                    val airport = departureJson.get("airport").asJsonObject.let { airportJson ->
                        Airport(
                                code = airportJson.get("code").asString,
                                name = airportJson.get("displayName").asString
                        )
                    }
                    Departure(airport, localDateTime)
                }
                val arrival = segmentJson.asJsonObject.get("arrival").asJsonObject.let { arrivalJson ->
                    val localDateTime = arrivalJson.get("isoDateTimeLocal").asString
                    val airport = arrivalJson.get("airport").asJsonObject.let { airportJson ->
                        Airport(
                                code = airportJson.get("code").asString,
                                name = airportJson.get("displayName").asString
                        )
                    }
                    Arrival(airport, localDateTime)
                }
                val flightDuration = segmentJson.asJsonObject.get("duration").asString
                val layover = segmentJson.asJsonObject.get("layover").asJsonObject.let {layoverJson ->
                    Layover(
                            duration = layoverJson.asJsonObject.get("duration").asString,
                            message = layoverJson.asJsonObject.get("message").asString
                    )
                }
                Flight(number, airline, departure, arrival, flightDuration, layover)
            }
            Leg(legId, flights, duration)
        }
        val options = resultJson.getAsJsonArray("optionsByFare").map { optionsByFareJson ->
            val options1 = optionsByFareJson.asJsonObject.getAsJsonArray("options").map {optionsJson ->
                val url = optionsJson.asJsonObject.get("url").asString
                val bookingId = optionsJson.asJsonObject.get("bookingId").asString
                val price = optionsJson.asJsonObject.get("displayPrice").asString
                val providerInfo = optionsJson.asJsonObject.get("providerInfo").let { providerInfoJson ->
                    val name = providerInfoJson.asJsonObject.get("displayName").asString
                    val logoUrl = providerInfoJson.asJsonObject.getAsJsonArray("logoUrls").map { logoUrlsJson ->
                        val providerUrl = logoUrlsJson.asJsonObject.get("image").asString
                        providerUrl
                    }[0]
                    ProviderInfo(name, logoUrl)
                }
                Option(url, bookingId, price, providerInfo)
            }
            options1
        }

        return Result(resultId, legs, options)

    }
}




