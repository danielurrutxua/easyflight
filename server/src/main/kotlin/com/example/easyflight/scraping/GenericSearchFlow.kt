package com.example.easyflight.scraping

import com.example.easyflight.flights.adapters.Result
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.scraping.enum.WebSources
import com.example.easyflight.scraping.util.UrlBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
abstract class GenericSearchFlow() {

    @Value("\${base.url.flights.kayak}")
    private lateinit var baseUrlKayak: String

    @Value("\${base.url.flights.momondo}")
    private lateinit var baseUrlMomondo: String

    @Value("\${url.flight.params}")
    private lateinit var urlFlightParams: String

    @Value("\${url.flight.params.with.return}")
    private lateinit var urlFlightParamsWithReturn: String


    private val logger: Logger = LoggerFactory.getLogger(GenericSearchFlow::class.java)

    fun execute(request: FlightSearchRequest, source: WebSources): List<Result> {
        logger.info("Scraping request for $source")
        return try {
            getKayakFLights(generateUrl(request, source))
        } catch (ex: Exception) {
            //driver.close()
            logger.error("Exception while scraping $source: ${ex.message}")
            listOf()
        }
    }

    private fun generateUrl(request: FlightSearchRequest, source: WebSources): String {
        return if (request.arrivalDate.isEmpty()) UrlBuilder()
                .setBase(
                        when (source) {
                            WebSources.KAYAK -> baseUrlKayak
                            WebSources.MOMONDO -> baseUrlMomondo
                        }.plus(urlFlightParams)
                ).setParam("origin", request.origin)
                .setParam("destination", request.destination)
                .setParam("departure-date", request.departureDate)
                .setParam("num-adults", request.adults)
                .build()
        else UrlBuilder()
                .setBase(
                        when (source) {
                            WebSources.KAYAK -> baseUrlKayak
                            WebSources.MOMONDO -> baseUrlMomondo
                        }.plus(urlFlightParamsWithReturn)
                ).setParam("origin", request.origin)
                .setParam("destination", request.destination)
                .setParam("departure-date", request.departureDate)
                .setParam("arrival-date", request.arrivalDate)
                .setParam("num-adults", request.adults)
                .build()

    }

    abstract fun getKayakFLights(url: String): List<Result>
}