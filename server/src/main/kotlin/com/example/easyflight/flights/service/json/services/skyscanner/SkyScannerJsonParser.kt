package com.example.easyflight.flights.service.json.services.skyscanner

import com.example.easyflight.agent.repository.AgentRepository
import com.example.easyflight.airline.repository.AirlineRepository
import com.example.easyflight.flights.adapters.response.*
import com.example.easyflight.flights.service.json.services.JsonParser
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory

class SkyScannerJsonParser(private val airlineRepository: AirlineRepository, private val agentRepository: AgentRepository) : JsonParser {
    private val LOGGER = LoggerFactory.getLogger(SkyScannerJsonParser::class.java)
    override fun execute(resultJson: JsonObject): List<Result> {
        return resultJson.getAsJsonArray("itineraries")?.let { itineraries ->
            itineraries.map { itinerary ->
                val resultId = itinerary.asJsonObject.get("id").asString
                val score = itinerary.asJsonObject.get("score").asDouble
                val legs = itinerary.asJsonObject.getAsJsonArray("leg_ids").map { legId ->
                    resultJson.getAsJsonArray("legs").filter { legJson ->
                        legJson.asJsonObject.get("id").asString.equals(legId.asString)
                    }[0].asJsonObject.let { legJson ->
                        val id = legJson.get("id").asString
                        val duration = convertMinutesToHoursAndMinutes(legJson.get("duration").asString)
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
                                        val url = airlineRepository.findUrlByName(name) ?: ""
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
                    var price = "333"
                    try{
                        price = pricingOption.asJsonObject.getAsJsonObject("price").get("amount").toString()
                    } catch(ex: Exception){
                        LOGGER.info(ex.message)
                    }
                    val url = pricingOption.asJsonObject.getAsJsonArray("items")[0].asJsonObject.get("url").asString
                    val agentId = pricingOption.asJsonObject.getAsJsonArray("items")[0].asJsonObject.get("agent_id").asString
                    val agent = resultJson.getAsJsonArray("agents").filter { agentJson ->
                        agentJson.asJsonObject.get("id").asString == agentId
                    }[0].let { agentJson ->
                        val name = agentJson.asJsonObject.get("name").asString
                        val url1 = agentRepository.findUrlByName(name) ?: ""
                        Agent(name, url1)
                    }
                    Option(url, bookingId, price, agent)
                }
                Result(resultId, legs, options, score)
            }
        }?.sortedByDescending { it.score } ?: emptyList()

    }

    private fun emptyList(): List<Result> {
        LOGGER.info("No itineraries found in response")
        return emptyList()
    }

    private fun convertMinutesToHoursAndMinutes(minutesString: String): String {
        val minutes = minutesString.toInt()
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h ${remainingMinutes}m"
    }


}
