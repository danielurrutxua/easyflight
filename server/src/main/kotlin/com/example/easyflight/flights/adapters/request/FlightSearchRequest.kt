package com.example.easyflight.flights.adapters.request

data class FlightSearchRequest(
        val origin: String,
        val destination: String,
        val departureDate: String,
        val arrivalDate: String,
        val adults: Int,
        val children: Int,
        val webSources: String
)
