package com.example.easyflight.airports.service

import com.example.easyflight.airports.exceptions.AirportNotFoundException
import com.example.easyflight.airports.model.Airport
import com.example.easyflight.airports.repository.AirportRepository
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.BufferedReader


@Service
class AirportService(
    private val repository: AirportRepository
) : AirportInterface {

    private val LOGGER = LoggerFactory.getLogger(AirportService::class.java)

    override fun searchStartingWith(searchText: String) = repository.loadAirportsStartingWith(searchText)

}