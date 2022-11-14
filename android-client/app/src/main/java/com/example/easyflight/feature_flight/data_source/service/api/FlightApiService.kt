package com.example.easyflight.feature_flight.data_source.service.api

import com.example.easyflight.feature_flight.domain.model.service.response.FlightsSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FlightApiService {

    @GET("flights/search")
    fun getFlights(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("departure_date") departureDate: String,
        @Query("arrival_date") arrivalDate: String,
        @Query("adults") numPassengers: String,
        @Query("children") children : String = "0"
    ): Call<List<FlightsSearch>>

    @GET("airports/search/starting-with")
    fun getAirportsStartingWith(@Query("staring-with") airportTyped: String): Call<List<String>>
}