package com.example.easyflight.feature_flight.domain.repository

import com.example.easyflight.feature_flight.data_source.service.FlightServiceDataSource
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import kotlinx.coroutines.flow.Flow

class FlightRepositoryImpl(private val flightServiceDataSource: FlightServiceDataSource): FlightRepository {

    override fun getFlights(flightSearchRequest: FlightSearchRequest): Flow<List<FlightsSearch>> {
        return flightServiceDataSource.getFlights(flightSearchRequest)
    }

    override fun getAirports(airportTyped: String): Flow<List<String>> {
        return flightServiceDataSource.getAirports(airportTyped)
    }
}