package com.example.easyflight.airports.controller

import com.example.easyflight.airports.service.AirportInterface
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/airports")
class AirportController(private val airportInterface: AirportInterface) {
    @GetMapping("/search-suggested")
    fun searchSuggested(@RequestParam("text") text: String, @RequestParam("limit") limit: Int) = airportInterface.searchSuggested(text, limit)
}