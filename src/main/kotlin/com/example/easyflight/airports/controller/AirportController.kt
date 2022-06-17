package com.example.easyflight.airports.controller

import com.example.easyflight.airports.model.Airport
import com.example.easyflight.airports.service.AirportService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/airports")
class AirportController(private val airportService: AirportService) {


    @PostMapping("/load-to-db")
    fun loadToDb() = airportService.loadToDb()

    @GetMapping("/search/starting-with")
    fun searchAirportsStartingWith(@RequestParam("search_text") searchText: String) = airportService.searchStartingWith(searchText)


}