package com.example.easyflight.flights.controller

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.exceptions.ScraperException
import com.example.easyflight.flights.service.FlightSearchInterface
import com.google.gson.Gson
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/flights")
@ResponseBody
class FlightController(private val flightSearchInterface: FlightSearchInterface) {

    @GetMapping("/search")
    fun search(@RequestParam("origin") origin: String,
               @RequestParam("destination") destination: String,
               @RequestParam("departure_date")  departureDate: String,
               @RequestParam(name = "arrival_date", required = false, defaultValue = "")  arrivalDate: String,
               @RequestParam(name = "adults", required = false, defaultValue = "1") adults: Int,
               @RequestParam(name = "children", required = false, defaultValue = "0") children: Int)
    : ResponseEntity<String> =
        try {
            val response = flightSearchInterface.performSearch(FlightSearchRequest(origin, destination, departureDate, arrivalDate, adults, children))
            ResponseEntity.accepted().body(Gson().toJson(response))
        } catch (ex: ScraperException) {
            ResponseEntity.internalServerError().build()
        }

}