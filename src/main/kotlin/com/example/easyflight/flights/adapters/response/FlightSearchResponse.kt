package com.example.easyflight.flights.adapters.response

import com.example.easyflight.flights.adapters.Flight
import com.example.easyflight.flights.adapters.Offer

data class FlightSearchResponse(
    val outboundFlight: Flight,
    val returnFlight: Flight,
    val offer: Offer,
    val airlineNames: List<String>
)