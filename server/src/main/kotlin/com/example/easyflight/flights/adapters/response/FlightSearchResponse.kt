package com.example.easyflight.flights.adapters.response

import com.example.easyflight.flights.adapters.Flight1
import com.example.easyflight.flights.adapters.Offer

data class FlightSearchResponse(
        val outboundFlight: Flight1,
        val returnFlight: Flight1?,
        val offer: Offer,
        val airlineNames: List<String>
)