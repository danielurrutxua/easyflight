package com.example.easyflight.flights.service.url

import com.example.easyflight.flights.service.url.services.UrlGenerator
import com.example.easyflight.flights.service.url.services.kayak.KayakUrlGenerator
import com.example.easyflight.flights.service.url.services.skyscanner.SkyScannerUrlGenerator
import com.example.easyflight.flights.service.source.WebSources

class UrlGeneratorFactory {
    companion object {
        fun create(webSource: WebSources): UrlGenerator {
            return when (webSource) {
                WebSources.KAYAK -> KayakUrlGenerator()
                WebSources.SKYSCANNER-> SkyScannerUrlGenerator()
                // agregar más casos según sea necesario
            }
        }
    }
}