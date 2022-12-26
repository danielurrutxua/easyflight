package com.example.easyflight.flights.service

import com.example.easyflight.scraping.kayak.ScraperComponent
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.FlightSearchResponse
import com.example.easyflight.scraping.enum.WebSources
import org.springframework.stereotype.Service

@Service
class FlightSearchService(private val scraperComponent: ScraperComponent): FlightSearchInterface {

     override fun performSearch(request: FlightSearchRequest): List<FlightSearchResponse> {
        return scraperComponent.execute(request, WebSources.KAYAK)
    }
}