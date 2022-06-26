package com.example.easyflight.feature_flight.domain.use_case

data class FlightUseCases (
    val getFlights: GetFlights,
    val getAirports: GetAirports,
    val openFlightUrl: OpenFlightUrl
        )