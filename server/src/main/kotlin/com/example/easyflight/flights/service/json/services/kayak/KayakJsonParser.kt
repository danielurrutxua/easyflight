package com.example.easyflight.flights.service.json.services.kayak

import com.example.easyflight.flights.adapters.response.*
import com.example.easyflight.flights.service.json.services.JsonParser
import com.google.gson.JsonObject

class KayakJsonParser : JsonParser {
    override fun execute(resultJson: JsonObject): List<Result> {
        resultJson.getAsJsonObject("results")
        val keys = resultJson.keySet().filter { key -> key.matches(Regex("[a-zA-Z0-9]{32}")) }

        return keys.map {
            val resultJsonF = resultJson.getAsJsonObject(it)

            val resultId = resultJsonF.get("resultId").asString
            val legs = resultJsonF.getAsJsonArray("legs").map { legJson ->
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
            val options = resultJsonF.getAsJsonArray("optionsByFare").map { optionsByFareJson ->
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

            Result(resultId, legs, options, null)
        }

    }
}
