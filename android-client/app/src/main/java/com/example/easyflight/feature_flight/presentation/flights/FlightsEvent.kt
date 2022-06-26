package com.example.easyflight.feature_flight.presentation.flights

import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch

sealed class FlightsEvent {
    data class Search(val flightSearchRequest: FlightSearchRequest): FlightsEvent()
    data class TypeDepartureAirport(val airport: String): FlightsEvent()
    data class TypeArrivalAirport(val airport: String): FlightsEvent()
    data class SelectFlight(val flightsSearch: FlightsSearch): FlightsEvent()
}