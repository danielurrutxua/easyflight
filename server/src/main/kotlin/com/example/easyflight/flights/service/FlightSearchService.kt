package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.adapters.response.Result
import com.example.easyflight.flights.service.api.ScraperApiCaller
import com.example.easyflight.flights.service.json.JsonParserFactory
import com.example.easyflight.flights.service.source.WebSources
import com.example.easyflight.flights.service.url.UrlGeneratorFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class FlightSearchService {

    companion object {
        suspend operator fun invoke(request: FlightSearchRequest): Map<WebSources, List<Result>> {
            val urlsMap = request.webSources.split(",")
                    .associateBy(
                            { WebSources.valueOf(it) },
                            { UrlGeneratorFactory.create(WebSources.valueOf(it)).invoke(request) }
                    )

            return coroutineScope {
                urlsMap.map { (source, url) ->
                    async(Dispatchers.IO) {
                        val response = ScraperApiCaller.invoke(source, url)
                        val resultList = JsonParserFactory.create(source).execute(stringResponseToJsonObject(response))
                        source to resultList
                    }
                }.awaitAll().toMap()
            }
        }
        private fun stringResponseToJsonObject(response: String?): JsonObject = Gson().fromJson(response, JsonObject::class.java)

    }




}