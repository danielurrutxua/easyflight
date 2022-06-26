package com.example.easyflight.feature_flight.domain.model.service.request

import java.time.LocalDate

data class FlightSearchRequest(
    val origin: String,
    val destination: String,
    val departureDate: LocalDate,
    val returnDate: LocalDate,
    val numPassengers: String
)