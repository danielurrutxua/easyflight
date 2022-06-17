package com.example.easyflight.flights.service

import com.example.easyflight.flights.KayakScraperComponent
import com.example.easyflight.flights.adapters.FlightSearchRequest
import com.example.easyflight.flights.adapters.FlightSearchResponse
import com.example.easyflight.flights.enum.WebSources
import org.springframework.stereotype.Service

@Service
class FlightSearchService(private val kayakScraperComponent: KayakScraperComponent) {

     fun performSearch(request: FlightSearchRequest): FlightSearchResponse {
        return kayakScraperComponent.execute(request, WebSources.KAYAK)
    }
}