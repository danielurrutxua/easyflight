package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.response.Result
import com.example.easyflight.flights.adapters.request.FlightSearchRequest

interface FlightSearchInterface {
    fun performSearch(request: FlightSearchRequest): List<Result>
}