package com.example.easyflight.flights.controller

import com.example.easyflight.flights.adapters.FlightSearchRequest
import com.example.easyflight.flights.adapters.FlightSearchResponse
import com.example.easyflight.flights.service.FlightSearchService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.awt.print.Book


@RestController
@RequestMapping("/flights")
class FlightController(private val flightSearchService: FlightSearchService) {

    @GetMapping("/search")
    fun search(@RequestParam("origin") origin: String,
               @RequestParam("destination") destination: String,
               @RequestParam("departure_date")  departureDate: String,
               @RequestParam("arrival_date")  arrivalDate: String,
               @RequestParam("adults") adults: Int,
               @RequestParam("children") children: Int)
    : FlightSearchResponse? = flightSearchService.performSearch(FlightSearchRequest(origin, destination, departureDate, arrivalDate, adults, children))


    @GetMapping("/")
    fun findFlights(): Collection<Book>? {
        return null
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(
            @PathVariable("id") id: String?, @RequestBody book: Book): Book {
        return book
    }

    @GetMapping("/foos/{id}")
    @ResponseBody
    fun getFooById(@PathVariable id: String): String? {
        return "ID: $id"
    }
}