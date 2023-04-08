package com.example.easyflight.airports.service

import com.example.easyflight.airports.model.Airport
import java.util.*

interface AirportInterface {
    fun searchSuggested(query: String, limit: Int): List<Airport>
}
