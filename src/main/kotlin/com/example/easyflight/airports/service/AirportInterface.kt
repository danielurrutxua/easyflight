package com.example.easyflight.airports.service

import com.example.easyflight.airports.model.Airport
import java.util.*

interface AirportInterface {

    fun loadToDb()
    fun searchStartingWith(searchText: String): List<Airport>
    fun getAirportName(iata: String): String
    fun findById(iata: String): Optional<Airport>
}
