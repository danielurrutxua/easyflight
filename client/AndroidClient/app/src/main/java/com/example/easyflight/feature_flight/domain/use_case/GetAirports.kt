package com.example.easyflight.feature_flight.domain.use_case

import com.example.easyflight.feature_flight.domain.repository.FlightRepository
import kotlinx.coroutines.flow.Flow

class GetAirports(private val flightRepository: FlightRepository) {

    operator fun invoke(
        airportTyped: String,
    ): Flow<List<String>> {
        return flightRepository.getAirports(airportTyped)
    }
}