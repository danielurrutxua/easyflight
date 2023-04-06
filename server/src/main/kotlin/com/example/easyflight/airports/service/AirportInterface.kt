package com.example.easyflight.airports.service

import com.example.easyflight.airports.model.Airport
import java.util.*

interface AirportInterface {

    fun searchStartingWith(searchText: String): List<Airport>
}
