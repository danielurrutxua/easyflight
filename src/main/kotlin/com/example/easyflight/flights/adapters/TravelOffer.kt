package com.example.easyflight.flights.adapters

data class TravelOffer(
    val outboundFlight: Flight,
    val returnFlight: Flight,
    val price: Int
)