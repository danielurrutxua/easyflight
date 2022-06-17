package com.example.easyflight.flights.adapters

data class SearchResponse (
        val departureFlights: List<Flight>,
        val returnFlights: List<Flight>
)