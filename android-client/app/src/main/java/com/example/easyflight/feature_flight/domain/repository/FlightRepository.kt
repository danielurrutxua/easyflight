package com.example.easyflight.feature_flight.domain.repository

import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getFlights(flightSearchRequest: FlightSearchRequest): Flow<List<FlightsSearch>?>
    fun getAirports(airportTyped: String): Flow<List<String>>
}