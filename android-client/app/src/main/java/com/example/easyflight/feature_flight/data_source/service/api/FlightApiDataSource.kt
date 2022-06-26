package com.example.easyflight.feature_flight.data_source.service.api

import android.util.Log
import com.example.easyflight.feature_flight.data_source.service.FlightServiceDataSource
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlightApiDataSource(private val flightsApiService: FlightApiService) :
    FlightServiceDataSource {

    private val tag = "FlightsApiDataSource"

    override fun getFlights(flightSearchRequest: FlightSearchRequest): Flow<List<FlightsSearch>> {

        return flow {
            try {
                val response = flightsApiService.getFlights(
                    flightSearchRequest.origin,
                flightSearchRequest.destination,
                flightSearchRequest.departureDate,
                flightSearchRequest.returnDate,
                flightSearchRequest.numPassengers).execute()

                response.body()?.let { this.emit(it) }
            } catch(t: Throwable){
                Log.e(tag, "error getting flights", t)
            }
        }
    }

    override fun getAirports(airportTyped: String): Flow<List<String>> {
        return flow {
            try {
                val response = flightsApiService.getAirportsStartingWith(
                    airportTyped
                ).execute()

                response.body()?.let { this.emit(it) }
            } catch(t: Throwable){
                Log.e(tag, "error getting airports", t)
            }
        }
    }
}