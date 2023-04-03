package com.example.easyflight.flights.service.api

import com.example.easyflight.flights.service.source.WebSources
import kong.unirest.Unirest
import org.slf4j.LoggerFactory

class ScraperApiCaller {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Companion::class.java)
        private const val URL = "http://scrapi:5000/scrape"
        private const val API_KEY = "v7RWO4ybCKkTlv1UfvOuOYrWJo9XybLF5AZXXmHk39OrTuxdC45SQYpYExViHtDgyFwQMPlefsHw8cT75hy5ZZoRJ6xXBaS5KTqvMLd1CMBLXeccPMCnsj7UMf1HqZ4P"

        fun invoke(source: WebSources, urlParam: String): String? {
            LOGGER.trace("Calling scraper ${source.name}")

            val response = Unirest.get(URL)
                    .header("Authorization", API_KEY)
                    .queryString("url", urlParam)
                    .asString()

            return if (response.isSuccess) {
                LOGGER.trace("${source.name} request successfully")
                response.body
            } else {
                LOGGER.error(response.statusText.plus(" - ").plus(response.body))
                null
            }
        }

    }



}