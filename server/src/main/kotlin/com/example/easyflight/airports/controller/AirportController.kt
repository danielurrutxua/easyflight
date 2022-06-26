package com.example.easyflight.airports.controller

import com.example.easyflight.airports.service.AirportInterface
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/airports")
class AirportController(private val airportInterface: AirportInterface) {


    @PostMapping("/load-to-db")
    fun loadToDb() = airportInterface.loadToDb()

    @GetMapping("/search/starting-with")
    fun searchAirportsStartingWith(@RequestParam("search_text") searchText: String) = airportInterface.searchStartingWith(searchText)


}