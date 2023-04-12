package com.example.easyflight.feature_flight.presentation.flights

import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import java.time.LocalDate

sealed class FlightsEvent {
    data class TypeDepartureAirport(val airport: String) : FlightsEvent()
    data class TypeArrivalAirport(val airport: String) : FlightsEvent()
    data class SetDepartureDate(val date: LocalDate) : FlightsEvent()
    data class SetReturnDate(val date: LocalDate) : FlightsEvent()
    data class SelectFlight(val flightsSearch: FlightsSearch) : FlightsEvent()
    data class SelectDepartureAirport(val airport: String) : FlightsEvent()
    data class UpdatePassengers(val total: Int) : FlightsEvent()
    data class Search(val roundTrip: Boolean) : FlightsEvent()
    data class SetShowOriginResults(val value: Boolean) : FlightsEvent()
    data class SetShowDestinationResults(val value: Boolean) : FlightsEvent()

    object SwapAirports : FlightsEvent()
    object ShowBottomSheet : FlightsEvent()
    object DismissBottomSheet : FlightsEvent()
    object ResetResults : FlightsEvent()
}