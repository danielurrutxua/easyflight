package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.FlightSearchRequest
import com.example.easyflight.flights.adapters.FlightSearchResponse

interface FlightSearchInterface {

    fun search(request: FlightSearchRequest): FlightSearchResponse
}