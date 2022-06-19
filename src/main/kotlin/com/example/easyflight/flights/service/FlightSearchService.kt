package com.example.easyflight.flights.service

import com.example.easyflight.scraping.kayak.KayakScraperComponent
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.FlightSearchResponse
import com.example.easyflight.scraping.enum.WebSources
import org.springframework.stereotype.Service

@Service
class FlightSearchService(private val kayakScraperComponent: KayakScraperComponent): FlightSearchInterface {

     override fun performSearch(request: FlightSearchRequest): List<FlightSearchResponse> {
        return kayakScraperComponent.execute(request, WebSources.KAYAK)
    }
}