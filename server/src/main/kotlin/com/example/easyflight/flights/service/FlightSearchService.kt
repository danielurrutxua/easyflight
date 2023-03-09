package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.Result
import com.example.easyflight.scraping.kayak.KayakScraper
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.scraping.enum.WebSources
import com.example.easyflight.scraping.kayak.ExpediaScraper
import org.springframework.stereotype.Service

@Service
class FlightSearchService(private val kayakScraper: KayakScraper, private val expediaScraper: ExpediaScraper): FlightSearchInterface {

     override fun performSearch(request: FlightSearchRequest): List<Result> {

//         expediaScraper.execute()
//         return  emptyList()

     return kayakScraper.execute(request, WebSources.KAYAK)
    }
}