package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.service.api.ScraperApiCaller
import com.example.easyflight.flights.service.json.JsonParserFactory
import com.example.easyflight.flights.service.source.WebSources
import com.example.easyflight.flights.service.url.UrlGeneratorFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FlightSearchService {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Companion::class.java)
        suspend operator fun invoke(request: FlightSearchRequest): String {
            val urlsMap = request.webSources.split(",")
                    .associateBy(
                            { WebSources.valueOf(it) },
                            { UrlGeneratorFactory.create(WebSources.valueOf(it)).invoke(request) }
                    )
            LOGGER.info(urlsMap.toString())

            return coroutineScope {
                val responseMap = urlsMap.map { (source, url) ->
                    async(Dispatchers.IO) {
                        val response = ScraperApiCaller.invoke(source, url)
                        val resultList = JsonParserFactory.create(source).execute(stringResponseToJsonObject(response))
                        LOGGER.info(source.name +": "+ resultList.size+ " results")
                        source to resultList
                    }
                }.awaitAll().toMap()
                Gson().toJson(responseMap)
            }
        }
        private fun stringResponseToJsonObject(response: String?): JsonObject = Gson().fromJson(response, JsonObject::class.java)

    }




}