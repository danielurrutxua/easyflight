package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.Result
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.scraping.kayak.KayakService
import com.example.easyflight.scraping.skyscanner.SkyScannerService
import org.springframework.stereotype.Service

@Service
class FlightSearchService(private val kayakService: KayakService, private val skyScannerService: SkyScannerService): FlightSearchInterface {

     override fun performSearch(request: FlightSearchRequest): List<Result> {

//         expediaScraper.execute()
//         return  emptyList()

         skyScannerService.getResults(request)
     return kayakService.getResults(request)
    }
}