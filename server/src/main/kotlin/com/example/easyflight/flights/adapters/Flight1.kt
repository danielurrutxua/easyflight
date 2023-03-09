package com.example.easyflight.flights.adapters

data class Flight1 (
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val scales: List<Scale>
        )