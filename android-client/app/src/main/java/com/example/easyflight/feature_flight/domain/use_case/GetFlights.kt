package com.example.easyflight.feature_flight.domain.use_case

import com.example.easyflight.feature_flight.domain.exceptions.InvalidSearchRequestDataException
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import com.example.easyflight.feature_flight.domain.repository.FlightRepository
import kotlinx.coroutines.flow.Flow

class GetFlights(private val flightRepository: FlightRepository) {


    @Throws(InvalidSearchRequestDataException::class)
    operator fun invoke(
        flightSearchRequest: FlightSearchRequest
    ): Flow<Map<String, List<com.example.easyflight.feature_flight.domain.model.service.response.Result>>> {
        return flightRepository.getFlights(flightSearchRequest)
    }
}