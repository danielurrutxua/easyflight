package com.example.easyflight.scraping.kayak

import com.example.easyflight.flights.adapters.*
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.scraping.ScraperApiCaller
import com.example.easyflight.scraping.util.UrlBuilder
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class KayakService {

    private val LOGGER = LoggerFactory.getLogger(KayakService::class.java)


    fun getResults(request: FlightSearchRequest): List<Result> {

        val jsonString = ScraperApiCaller.kayak(generateUrl(request))

        return if (jsonString.isNullOrBlank()) {
            emptyList()
        } else {
            val resultsJson = Gson().fromJson(jsonString, JsonObject::class.java).getAsJsonObject("results")
            val keys = resultsJson.keySet().filter { key -> key.matches(Regex("[a-zA-Z0-9]{32}")) }

            LOGGER.trace("${keys.size} result keys found")

            keys.map {
                LOGGER.trace("Extracting key: $it")
                getResult(resultsJson.getAsJsonObject(it))
            }
        }
    }

    private fun getResult(resultJson: JsonObject): Result {
        val resultId = resultJson.get("resultId").asString
        val legs = resultJson.getAsJsonArray("legs").map { legJson ->
            val legId = legJson.asJsonObject.get("legId").asString
            val duration = legJson.asJsonObject.get("legDurationDisplay").asString
            val segments = legJson.asJsonObject.getAsJsonArray("segments").map { segmentJson ->
                val number = segmentJson.asJsonObject.get("flightNumber").asString
                val airline = segmentJson.asJsonObject.get("airline").asJsonObject.let { airlineJson ->
                    Airline(
                            code = airlineJson.get("code").asString,
                            name = airlineJson.get("name").asString,
                            logoUrl = airlineJson.get("logoUrl").asString
                    )
                }
                val departure = segmentJson.asJsonObject.get("departure").asJsonObject.let { departureJson ->
                    val localDateTime = departureJson.get("isoDateTimeLocal").asString
                    val airport = departureJson.get("airport").asJsonObject.let { airportJson ->
                        Airport(
                                code = airportJson.get("code").asString,
                                name = airportJson.get("displayName").asString
                        )
                    }
                    Departure(airport, localDateTime)
                }
                val arrival = segmentJson.asJsonObject.get("arrival").asJsonObject.let { arrivalJson ->
                    val localDateTime = arrivalJson.get("isoDateTimeLocal").asString
                    val airport = arrivalJson.get("airport").asJsonObject.let { airportJson ->
                        Airport(
                                code = airportJson.get("code").asString,
                                name = airportJson.get("displayName").asString
                        )
                    }
                    Arrival(airport, localDateTime)
                }
                val flightDuration = segmentJson.asJsonObject.get("duration").asString
                val layover = segmentJson.asJsonObject.get("layover")?.asJsonObject?.let { layoverJson ->
                    Layover(
                            duration = layoverJson.asJsonObject.get("duration").asString,
                            message = layoverJson.asJsonObject.get("message").asString
                    )
                }
                Segment(number, airline, departure, arrival, flightDuration, layover)
            }
            Leg(legId, segments, duration)
        }
        val options = resultJson.getAsJsonArray("optionsByFare").map { optionsByFareJson ->
            val options1 = optionsByFareJson.asJsonObject.getAsJsonArray("options").map { optionsJson ->
                val url = optionsJson.asJsonObject.get("url").asString
                val bookingId = optionsJson.asJsonObject.get("bookingId").asString
                val price = optionsJson.asJsonObject.get("displayPrice").asString
                val agent = optionsJson.asJsonObject.get("providerInfo").let { providerInfoJson ->
                    val name = providerInfoJson.asJsonObject.get("displayName").asString
                    val logoUrl = providerInfoJson.asJsonObject.getAsJsonArray("logoUrls").map { logoUrlsJson ->
                        val providerUrl = logoUrlsJson.asJsonObject.get("image").asString
                        providerUrl
                    }[0]
                    Agent(name, logoUrl)
                }
                Option(url, bookingId, price, agent)
            }
            options1
        }[0]

        return Result(resultId, legs, options, null)

    }

    private fun generateUrl(request: FlightSearchRequest): String {
        val baseUrl = "https://www.kayak.es/flights/"
        val urlParamsWithoutReturn = "{origin}-{destination}/{departure-date}/{num-adults}adults?sort=bestflight_a"
        val urlParamsWithReturn = "{origin}-{destination}/{departure-date}/{arrival-date}/{num-adults}adults?sort=bestflight_a"
        return if (request.arrivalDate.isEmpty()) UrlBuilder()
                .setBase(baseUrl.plus(urlParamsWithoutReturn))
                .setParam("origin", request.origin)
                .setParam("destination", request.destination)
                .setParam("departure-date", request.departureDate)
                .setParam("num-adults", request.adults)
                .build()
        else UrlBuilder()
                .setBase(baseUrl.plus(urlParamsWithReturn))
                .setParam("origin", request.origin)
                .setParam("destination", request.destination)
                .setParam("departure-date", request.departureDate)
                .setParam("arrival-date", request.arrivalDate)
                .setParam("num-adults", request.adults)
                .build()

    }
}




