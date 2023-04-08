package com.example.easyflight.feature_flight.data_source.service

import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import kotlinx.coroutines.flow.Flow

interface FlightServiceDataSource {

    fun getFlights(flightSearchRequest: FlightSearchRequest): Flow<List<FlightsSearch>?>
    fun getAirports(airportTyped: String, limit: Int): Flow<List<Airport>>
}
