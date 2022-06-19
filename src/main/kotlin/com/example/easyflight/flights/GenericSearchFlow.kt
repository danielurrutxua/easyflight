package com.example.easyflight.flights

import com.example.easyflight.flights.adapters.FlightSearchRequest
import com.example.easyflight.flights.adapters.FlightResponse
import com.example.easyflight.flights.enum.WebSources
import com.example.easyflight.flights.util.UrlBuilder
import com.example.easyflight.flights.util.drivers.DriverInitializer
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

    protected lateinit var driver: RemoteWebDriver

    private val logger: Logger = LoggerFactory.getLogger(GenericSearchFlow::class.java)

    fun execute(request: FlightSearchRequest, source: WebSources): List<FlightResponse> {
        logger.info("Scraping request for $source")
        return try {
            driver = driverInitializer.initialize(generateUrl(request, source))
            logger.info("Prepare screen before scraping: IN")
            prepareScreenForScraping()
            logger.info("Prepare screen before scraping: OUT")
            val document = Jsoup.parse(driver.pageSource)
            //driver.quit()
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

    protected abstract fun extractFlights(document: Document, destination: String): List<FlightResponse>

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