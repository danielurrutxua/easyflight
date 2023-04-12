package com.example.easyflight.feature_flight.domain.repository

import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getFlights(flightSearchRequest: FlightSearchRequest): Flow<Map<String,List<com.example.easyflight.feature_flight.domain.model.service.response.Result>>>
    fun getAirports(airportTyped: String, limit: Int): Flow<List<Airport>>
}