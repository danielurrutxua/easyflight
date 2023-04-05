package com.example.easyflight.feature_flight.presentation.flights

import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import java.time.LocalDate

data class FlightsState(
    val flightsSearch: List<FlightsSearch> = emptyList(),
    val departureAirport: String = "Madrid, España",
    val destinationAirport: String = "",
    val suggestedAirports: List<String> = emptyList(),
    val departureDate: LocalDate = LocalDate.now(),
    val returnDate: LocalDate = LocalDate.now().plusDays(3),
    val numPassengers: Int = 1,
    val showBottomSheet: Boolean = false
)