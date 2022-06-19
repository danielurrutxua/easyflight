package com.example.easyflight.flights.adapters

data class FlightResponse(
    val outboundFlight: Flight,
    val returnFlight: Flight,
    val offers: List<Offer>,
    val airlineNames: List<String>
)