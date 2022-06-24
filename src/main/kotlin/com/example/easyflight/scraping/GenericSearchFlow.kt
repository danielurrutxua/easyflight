package com.example.easyflight.scraping

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.FlightSearchResponse
import com.example.easyflight.scraping.enum.WebSources
import com.example.easyflight.scraping.util.UrlBuilder
import com.example.easyflight.scraping.drivers.DriverInitializer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.remote.RemoteWebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
abstract class GenericSearchFlow(
    private val driverInitializer: DriverInitializer,
    private val urlBuilder: UrlBuilder
) {

    @Value("\${base.url.flights.kayak}")
    private lateinit var baseUrlKayak: String

    @Value("\${base.url.flights.momondo}")
    private lateinit var baseUrlMomondo: String

    @Value("\${url.flight.params}")
    private lateinit var urlFlightParams: String

    protected lateinit var driver: RemoteWebDriver

    private val logger: Logger = LoggerFactory.getLogger(GenericSearchFlow::class.java)

    fun execute(request: FlightSearchRequest, source: WebSources): List<FlightSearchResponse> {
        logger.info("Scraping request for $source")
        return try {
            driver = driverInitializer.initialize(generateUrl(request, source))
            logger.info("Prepare screen before scraping: IN")
            prepareScreenForScraping()
            logger.info("Prepare screen before scraping: OUT")
            val document = Jsoup.parse(driver.pageSource)
            driver.quit()
            logger.info("Extract flights: IN")
            val travelOffers = extractFlights(document, request.destination)
            logger.info("Extract flights: OUT")
            logger.info("Scraping request for $source completed")

            travelOffers
        } catch (ex:Exception) {
            logger.error("Exception while scraping $source: ${ex.message}")
            listOf()
        }
    }

    protected abstract fun prepareScreenForScraping()

    protected abstract fun extractFlights(document: Document, destination: String): List<FlightSearchResponse>

    private fun generateUrl(request: FlightSearchRequest, source: WebSources) = urlBuilder
        .setBaseUrl(
            when (source) {
                WebSources.KAYAK -> baseUrlKayak
                WebSources.MOMONDO -> baseUrlMomondo
            }.plus(urlFlightParams)
        ).setParamIntoUrl("origin", request.origin)
        .setParamIntoUrl("destination", request.destination)
        .setParamIntoUrl("departure-date", request.departureDate)
        .setParamIntoUrl("arrival-date", request.arrivalDate)
        .setParamIntoUrl("num-adults", request.adults)
        .build()

}