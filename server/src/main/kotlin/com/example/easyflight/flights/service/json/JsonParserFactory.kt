package com.example.easyflight.flights.service.json

import com.example.easyflight.airline.repository.AirlineRepository
import com.example.easyflight.flights.service.json.services.JsonParser
import com.example.easyflight.flights.service.json.services.kayak.KayakJsonParser
import com.example.easyflight.flights.service.json.services.skyscanner.SkyScannerJsonParser
import com.example.easyflight.flights.service.source.WebSources
import org.springframework.stereotype.Service

@Service
class JsonParserFactory(private val airlineRepository: AirlineRepository) {
    fun create(webSource: WebSources): JsonParser {
        return when (webSource) {
            WebSources.KAYAK -> KayakJsonParser(airlineRepository)
            WebSources.SKYSCANNER -> SkyScannerJsonParser(airlineRepository)
            // agregar más casos según sea necesario
        }
    }
}