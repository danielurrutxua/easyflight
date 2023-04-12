package com.example.easyflight.airports.service

import com.example.easyflight.airline.repository.AirlineRepository
import com.example.easyflight.airports.repository.AirportRepository
import org.springframework.stereotype.Service


@Service
class AirportService(
    private val repository: AirportRepository
) : AirportInterface {
    override fun searchSuggested(query: String, limit: Int) = repository.searchSuggested(query, limit)

}