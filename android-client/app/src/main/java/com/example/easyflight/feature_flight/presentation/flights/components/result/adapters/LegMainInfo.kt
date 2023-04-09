package com.example.easyflight.feature_flight.presentation.flights.components.result.adapters

data class LegMainInfo(
    val origin: String,
    val destination: String,
    val timeO: String,
    val timeD: String,
    val nextDay: Boolean = false,
    val stop: Boolean = false
)