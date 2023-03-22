package com.example.easyflight.flights.service.json

import com.example.easyflight.flights.service.json.services.JsonParser
import com.example.easyflight.flights.service.json.services.kayak.KayakJsonParser
import com.example.easyflight.flights.service.json.services.skyscanner.SkyScannerJsonParser
import com.example.easyflight.flights.service.source.WebSources

class JsonParserFactory {
    companion object {
        fun create(webSource: WebSources): JsonParser {
            return when (webSource) {
                WebSources.KAYAK -> KayakJsonParser()
                WebSources.SKYSCANNER-> SkyScannerJsonParser()
                // agregar más casos según sea necesario
            }
        }
    }
}