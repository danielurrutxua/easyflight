package com.example.easyflight.scraping

import kong.unirest.Unirest
import org.slf4j.LoggerFactory

class ScraperApiCaller {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Companion::class.java)
        private const val BASE_URL = "http://localhost:5000"
        private const val API_KEY = "v7RWO4ybCKkTlv1UfvOuOYrWJo9XybLF5AZXXmHk39OrTuxdC45SQYpYExViHtDgyFwQMPlefsHw8cT75hy5ZZoRJ6xXBaS5KTqvMLd1CMBLXeccPMCnsj7UMf1HqZ4P"

        fun kayak(url: String): String? {

            LOGGER.trace("Calling scraper API /kayak")

            val response = Unirest.get("$BASE_URL/kayak")
                    .header("Authorization", API_KEY)
                    .queryString("url", url)
                    .asString()

            return if (response.isSuccess) {
                LOGGER.trace("/kayak request successfully")
                response.body
            } else {
                LOGGER.error(response.statusText.plus(" - ").plus(response.body))
                null
            }

        }

        fun skyscanner(generateUrl: String): String? {
            return null
        }

    }



}