package com.example.easyflight.feature_flight.presentation.flights.components.result.adapters

import com.example.easyflight.feature_flight.domain.model.service.response.Airline

data class LegMainInfo(
    val origin: String,
    val destination: String,
    val timeO: String,
    val timeD: String,
    val airline: Airline,
    val duration: String,
    val nextDay: Boolean = false,
    val stops: Int = 0
)