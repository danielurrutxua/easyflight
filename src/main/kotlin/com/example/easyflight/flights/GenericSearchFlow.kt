package com.example.easyflight.flights

import com.example.easyflight.flights.adapters.FlightSearchRequest
import com.example.easyflight.flights.adapters.FlightSearchResponse
import com.example.easyflight.flights.adapters.SearchResponse
import com.example.easyflight.flights.enum.WebSources
import com.example.easyflight.flights.util.ChromeDriverInitializer
import com.example.easyflight.flights.util.UrlBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
abstract class GenericSearchFlow(
    private val chromeDriverInitializer: ChromeDriverInitializer,
    private val urlBuilder: UrlBuilder
) {

    @Value("\${base.url.flights.kayak}")
    private lateinit var baseUrlKayak: String

    protected lateinit var driver: ChromeDriver

    private val LOGGER: Logger = LoggerFactory.getLogger(GenericSearchFlow::class.java)

    fun execute(request: FlightSearchRequest, source: WebSources): FlightSearchResponse {
        LOGGER.info("Scraping request for $source")
        return try {
            driver = chromeDriverInitializer.initialize(generateUrl(request, source))
            LOGGER.info("Prepare screen before scraping: IN")
            prepareScreenForScraping()
            LOGGER.info("Prepare screen before scraping: OUT")
            val document = Jsoup.parse(driver.pageSource)
//            driver.quit()
            LOGGER.info("Extract flights: IN")
            val flights = extractFlights(document, request.destination)
            LOGGER.info("Extract flights: OUT")
            LOGGER.info("Scraping request for $source completed")
            FlightSearchResponse(flights)
        } catch (ex:Exception) {
            LOGGER.error("Exception while scraping $source: ${ex.message}")
            FlightSearchResponse(listOf())
        }
    }

    protected abstract fun prepareScreenForScraping()

    protected abstract fun extractFlights(document: Document, destination: String): List<SearchResponse>

    private fun generateUrl(request: FlightSearchRequest, source: WebSources) = urlBuilder
        .setBaseUrl(
            when (source) {
                WebSources.KAYAK -> baseUrlKayak
            }
        ).setParamIntoUrl("origin", request.origin)
        .setParamIntoUrl("destination", request.destination)
        .setParamIntoUrl("departure-date", request.departureDate)
        .setParamIntoUrl("arrival-date", request.arrivalDate)
        .setParamIntoUrl("num-adults", request.adults)
        .build()

}