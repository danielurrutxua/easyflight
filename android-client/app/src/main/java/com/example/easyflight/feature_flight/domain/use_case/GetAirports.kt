package com.example.easyflight.feature_flight.domain.use_case

import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.feature_flight.domain.repository.FlightRepository
import kotlinx.coroutines.flow.Flow

class GetAirports(private val flightRepository: FlightRepository) {

    operator fun invoke(
        airportTyped: String,
        limit: Int
    ): Flow<List<Airport>> { return flightRepository.getAirports(airportTyped, limit)
    }
}