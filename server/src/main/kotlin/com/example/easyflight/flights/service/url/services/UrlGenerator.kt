package com.example.easyflight.flights.service.url.services

import com.example.easyflight.flights.adapters.request.FlightSearchRequest

interface UrlGenerator {
    fun invoke(request: FlightSearchRequest): String
}