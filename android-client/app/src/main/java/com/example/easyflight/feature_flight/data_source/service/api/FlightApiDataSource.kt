package com.example.easyflight.feature_flight.data_source.service.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easyflight.feature_flight.data_source.service.FlightServiceDataSource
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import com.example.easyflight.feature_flight.presentation.util.searchRequestFormat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightApiDataSource(private val flightsApiService: FlightApiService) :
    FlightServiceDataSource {

    private val tag = "FlightsApiDataSource"


    override fun getFlights(flightSearchRequest: FlightSearchRequest) = callbackFlow {

        flightsApiService.getFlights(
            flightSearchRequest.origin,
            flightSearchRequest.destination,
            flightSearchRequest.departureDate.toString(),
            flightSearchRequest.returnDate.toString(),
            flightSearchRequest.numPassengers
        ).enqueue(object : Callback<List<FlightsSearch>> {
            override fun onResponse(
                call: Call<List<FlightsSearch>>,
                response: Response<List<FlightsSearch>>
            ) {
                trySend(response.body())
            }

            override fun onFailure(call: Call<List<FlightsSearch>>, t: Throwable) {
                Log.e(tag, "error getting flights", t)
            }

        })
    }

    override fun getAirports(airportTyped: String) = callbackFlow {
        flightsApiService.getAirportsStartingWith(
            airportTyped
        ).enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                trySend(response.body()!!)
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e(tag, "error getting airports", t)
            }

        })
    }
}