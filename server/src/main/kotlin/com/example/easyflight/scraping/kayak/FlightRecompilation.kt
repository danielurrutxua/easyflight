package com.example.easyflight.scraping.kayak

import org.springframework.stereotype.Service

@Service
class FlightRecompilation {
    companion object {
        const val KAYAK_BASE_URL = "https://www.kayak.com/flights"
        const val BOOKING_BASE_URL = "https://www.booking.com/flights"
        const val EXPEDIA_BASE_URL = "https://www.expedia.com/Flights"
        const val SKYSCANNER_BASE_URL = "https://www.skyscanner.com/flights"
        const val GOOGLE_FLIGHTS_BASE_URL = "https://www.google.com/flights"
    }




}