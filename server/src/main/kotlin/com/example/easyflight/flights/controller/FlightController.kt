package com.example.easyflight.flights.controller

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.Result
import com.example.easyflight.flights.exceptions.ScraperException
import com.example.easyflight.flights.service.FlightSearchInterface
import com.example.easyflight.flights.service.FlightSearchService
import com.example.easyflight.flights.service.source.WebSources
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/flights")
@ResponseBody
class FlightController {

    @GetMapping("/search", produces = ["application/json"])
    suspend fun search(@RequestParam("origin") origin: String,
               @RequestParam("destination") destination: String,
               @RequestParam("departure_date") departureDate: String,
               @RequestParam(name = "arrival_date", required = false, defaultValue = "") arrivalDate: String,
               @RequestParam(name = "adults", required = false, defaultValue = "1") adults: Int,
               @RequestParam(name = "children", required = false, defaultValue = "0") children: Int,
               @RequestParam(name = "web_sources", required = false) webSource: String)
            : ResponseEntity<String> {

        return try {
            val response = coroutineScope {
                FlightSearchService.invoke(FlightSearchRequest(origin, destination, departureDate, arrivalDate, adults, children, webSource))
            }
            ResponseEntity.accepted().body(response)
        } catch (ex: ScraperException) {
            ResponseEntity.internalServerError().build()
        }
    }
}