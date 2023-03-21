package com.example.easyflight.scraping.skyscanner

import com.example.easyflight.flights.adapters.*
import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.scraping.util.UrlBuilder
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component


@Component
class SkyScannerService(private val resourceLoader: ResourceLoader) {

    private val LOGGER = LoggerFactory.getLogger(SkyScannerService::class.java)

    fun getResults(request: FlightSearchRequest): List<Result> {

        val resource = ClassPathResource("responses.json")
        val jsonString = resource.inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        val result = getResult(jsonObject)
        return result.sortedBy {
            it.score
        }.reversed()
    }

    private fun getResult(resultJson: JsonObject): List<Result> {
        return resultJson.getAsJsonArray("itineraries").map { itinerary ->
            val resultId = itinerary.asJsonObject.get("id").asString
            val score = itinerary.asJsonObject.get("score").asDouble
            val legs = itinerary.asJsonObject.getAsJsonArray("leg_ids").map { legId ->
                resultJson.getAsJsonArray("legs").filter { legJson ->
                    legJson.asJsonObject.get("id").asString.equals(legId.asString)
                }[0].asJsonObject.let { legJson ->
                    val id = legJson.get("id").asString
                    val duration = legJson.get("duration").asString
                    val segments = legJson.getAsJsonArray("segment_ids").map { segmentId ->
                        resultJson.asJsonObject.getAsJsonArray("segments").filter { segmentJson ->
                            segmentJson.asJsonObject.get("id").asString.equals(segmentId.asString)
                        }[0].asJsonObject.let { segmentJson ->
                            val number = segmentJson.get("marketing_flight_number").asString
                            val durationS = segmentJson.get("duration").asString
                            val airline = segmentJson.get("marketing_carrier_id").asInt.let { carrierId ->
                                resultJson.getAsJsonArray("carriers").filter { carrierJson ->
                                    carrierJson.asJsonObject.get("id").asInt == carrierId
                                }[0].let { carrierJson ->
                                    val name = carrierJson.asJsonObject.get("name").asString
                                    val code = carrierJson.asJsonObject.get("alt_id").asString
                                    val url = "PENDIENTE"
                                    Airline(code, name, url)
                                }
                            }
                            val departure = segmentJson.get("origin_place_id").asInt.let { placeId ->
                                val date = segmentJson.get("departure").asString
                                val airport = resultJson.getAsJsonArray("places").filter { placeJson ->
                                    placeJson.asJsonObject.get("id").asInt == placeId
                                }[0].let { placeJson ->
                                    val name = placeJson.asJsonObject.get("name").asString
                                    val code = placeJson.asJsonObject.get("alt_id").asString
                                    Airport(code, name)
                                }
                                Departure(airport, date)
                            }
                            val arrival = segmentJson.get("destination_place_id").asInt.let { placeId ->
                                val date = segmentJson.get("arrival").asString
                                val airport = resultJson.getAsJsonArray("places").filter { placeJson ->
                                    placeJson.asJsonObject.get("id").asInt == placeId
                                }[0].let { placeJson ->
                                    val name = placeJson.asJsonObject.get("name").asString
                                    val code = placeJson.asJsonObject.get("alt_id").asString
                                    Airport(code, name)
                                }
                                Arrival(airport, date)
                            }
                            Segment(number, airline, departure, arrival, durationS, null)
                        }
                    }
                    Leg(id, segments, duration)
                }
            }
            val options = itinerary.asJsonObject.get("pricing_options").asJsonArray.map { pricingOption ->
                val bookingId = pricingOption.asJsonObject.get("id").asString
                val price = pricingOption.asJsonObject.getAsJsonObject("price").get("amount")?.asDouble.toString()
                val url = pricingOption.asJsonObject.getAsJsonArray("items")[0].asJsonObject.get("url").asString
                val agentId = pricingOption.asJsonObject.getAsJsonArray("items")[0].asJsonObject.get("agent_id").asString
                val agent = resultJson.getAsJsonArray("agents").filter { agentJson ->
                    agentJson.asJsonObject.get("id").asString == agentId
                }[0].let { agentJson ->
                    val name = agentJson.asJsonObject.get("name").asString
                    val url1 = "PENDIENTE"
                    Agent(name, url1)
                }
                Option(url, bookingId, price, agent)
            }
            Result(resultId, legs, options, score)
        }

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




