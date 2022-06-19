package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.FlightSearchResponse

interface FlightSearchInterface {
    fun performSearch(request: FlightSearchRequest): List<FlightSearchResponse>
}