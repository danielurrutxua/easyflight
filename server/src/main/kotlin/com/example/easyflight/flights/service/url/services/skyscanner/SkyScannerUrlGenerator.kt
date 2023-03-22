package com.example.easyflight.flights.service.url.services.skyscanner

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.service.url.services.UrlGenerator
import com.example.easyflight.flights.service.url.services.util.UrlBuilder

class SkyScannerUrlGenerator : UrlGenerator {

    override fun invoke(request: FlightSearchRequest) =
            if (request.arrivalDate.isEmpty()) getWithoutReturn(request)
            else getWithReturn(request)

    //TODO num children
    private fun getWithReturn(request: FlightSearchRequest): String =
            UrlBuilder()
                    .setBase(BASE_URL.plus(URL_WITH_RETURN))
                    .setParam("origin", request.origin)
                    .setParam("destination", request.destination)
                    .setParam("departure-date", request.departureDate)
                    .setParam("arrival-date", request.arrivalDate)
                    .setParam("num-adults", request.adults)
                    .build()

    private fun getWithoutReturn(request: FlightSearchRequest) =
            UrlBuilder()
                    .setBase(BASE_URL.plus(URL_WITHOUT_RETURN))
                    .setParam("origin", request.origin)
                    .setParam("destination", request.destination)
                    .setParam("departure-date", request.departureDate)
                    .setParam("num-adults", request.adults)
                    .build()

    companion object {
        private const val BASE_URL = "https://www.skyscanner.es/transporte/vuelos/"
        private const val URL_WITHOUT_RETURN = "{origin}/{destination}/{departure-date}/?adults={num-adults}&adultsv2={num-adults}&cabinclass=economy&childrenv2=&inboundaltsenabled=false&infants=0"
        private const val URL_WITH_RETURN = "{origin}/{destination}/{departure-date}/{arrival-date}/?adults={num-adults}&adultsv2={num-adults}&cabinclass=economy&childrenv2=&inboundaltsenabled=false&infants=0"

    }
}


