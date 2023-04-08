package com.example.easyflight.feature_flight.data_source.service.api

import android.util.Log
import com.example.easyflight.feature_flight.data_source.service.FlightServiceDataSource
import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightApiDataSource(private val flightsApiService: FlightApiService) :
    FlightServiceDataSource {

    private val tag = "FlightsApiDataSource"


    override fun getFlights(flightSearchRequest: FlightSearchRequest) =
        flow {
            try {
                val results = flightsApiService.getFlights(
                    flightSearchRequest.origin,
                    flightSearchRequest.destination,
                    flightSearchRequest.departureDate.toString(),
                    flightSearchRequest.returnDate.toString(),
                    flightSearchRequest.numPassengers
                )
                emit(results)
            } catch (e: Exception) {
                Log.e(tag, "error getting flights", e)
            }
        }

    override fun getAirports(airportTyped: String, limit: Int): Flow<List<Airport>> {
        return flow {
            try {
                val airports = flightsApiService.getAirportsStartingWith(airportTyped, limit)
                emit(airports)
            } catch (e: Exception) {
                Log.e(tag, "error getting airports", e)
            }
        }
    }
}