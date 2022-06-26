package com.example.easyflight.feature_flight.domain.model.service.response

import com.example.easyflight.feature_flight.domain.model.Flight
import com.example.easyflight.feature_flight.domain.model.Offer

data class FlightsSearch(
    val outboundFlight: Flight,
    val returnFlight: Flight,
    val offer: Offer,
    val airlineNames: List<String>
)