package com.example.easyflight.feature_flight.presentation.flights

import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import java.time.LocalDate

data class FlightsState(

    //Airports
    val departureAirport: String = "Bilbao, Spain (BIO)",
    val destinationAirport: String = "",
    val suggestedAirports: List<Airport> = emptyList(),
    val showOriginResults: Boolean = false,
    val showDestinationResults: Boolean = false,
    val showOriginEmptyError: Boolean = false,
    val showDestinationEmptyError: Boolean = false,


    //Passengers
    val numPassengers: Int = 1,
    val showBottomSheet: Boolean = false,

    //Dates
    val departureDate: LocalDate = LocalDate.now(),
    val returnDate: LocalDate = LocalDate.now().plusDays(3),
    val showDepartureDateError: Boolean = false,
    val showReturnDateError: Boolean = false,


    //Search
    val searchResults: List<FlightsSearch> = emptyList(),
    val isLoading: Boolean = false





    //Errors

)

