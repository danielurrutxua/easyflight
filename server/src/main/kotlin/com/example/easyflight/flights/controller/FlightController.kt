package com.example.easyflight.flights.controller

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.FlightSearchResponse
import com.example.easyflight.flights.exceptions.ScraperException
import com.example.easyflight.flights.service.FlightSearchInterface
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/flights")
class FlightController(private val flightSearchInterface: FlightSearchInterface) {

    @GetMapping("/search")
    fun search(@RequestParam("origin") origin: String,
               @RequestParam("destination") destination: String,
               @RequestParam("departure_date")  departureDate: String,
               @RequestParam("arrival_date")  arrivalDate: String,
               @RequestParam("adults") adults: Int,
               @RequestParam("children") children: Int)
    : ResponseEntity<List<FlightSearchResponse>> =
        try {
            ResponseEntity.accepted().body(flightSearchInterface.performSearch(FlightSearchRequest(origin, destination, departureDate, arrivalDate, adults, children)))
        } catch (ex: ScraperException) {
            ResponseEntity.internalServerError().build()
        }

}