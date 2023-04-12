package com.example.easyflight.feature_flight.data_source.service.api

import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import retrofit2.http.GET
import retrofit2.http.Query


interface FlightApiService {

    @GET("flights/search")
    suspend fun getFlights(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("departure_date") departureDate: String,
        @Query("arrival_date") arrivalDate: String,
        @Query("adults") numPassengers: String,
        @Query("children") children : String = "0",
        @Query("web_sources") webSources: String = "KAYAK,SKYSCANNER"
    ): Map<String, List<Result>>

    @GET("airports/search-suggested")
    suspend fun getAirportsStartingWith(@Query("text") airportTyped: String, @Query("limit") limit: Int): List<Airport>
}