package com.example.easyflight.feature_flight.domain.use_case

import com.example.easyflight.feature_flight.domain.exceptions.InvalidDestinationAirportException
import com.example.easyflight.feature_flight.domain.exceptions.InvalidOriginAirportException
import com.example.easyflight.feature_flight.domain.exceptions.InvalidReturnDateException
import com.example.easyflight.feature_flight.domain.exceptions.InvalidSearchRequestDataException
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import com.example.easyflight.feature_flight.domain.repository.FlightRepository
import com.example.easyflight.feature_flight.domain.util.FlightOrder
import com.example.easyflight.feature_flight.domain.util.OrderType
import kotlinx.coroutines.flow.Flow

class GetFlights(private val flightRepository: FlightRepository) {


    @Throws(InvalidSearchRequestDataException::class)
    operator fun invoke(
        flightSearchRequest: FlightSearchRequest,
        flightOrder: FlightOrder = FlightOrder.Price(OrderType.Ascending)
    ): Flow<List<FlightsSearch>> {
        if(flightSearchRequest.origin.isBlank())  {
            throw InvalidOriginAirportException("Must choose a valid Airport")
        }
        if(flightSearchRequest.destination.isBlank()) {
         throw InvalidDestinationAirportException("Must choose a valid Airport")
        }
        if(flightSearchRequest.departureDate > flightSearchRequest.returnDate) {
            throw InvalidReturnDateException("Return date is not valid")
        }
        return flightRepository.getFlights(flightSearchRequest)
    }
}