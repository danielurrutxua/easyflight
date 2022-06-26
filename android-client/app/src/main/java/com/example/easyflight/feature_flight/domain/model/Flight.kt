package com.example.easyflight.feature_flight.domain.model

data class Flight (
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val scales: List<Scale>
)