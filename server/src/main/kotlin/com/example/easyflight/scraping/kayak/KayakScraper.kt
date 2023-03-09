package com.example.easyflight.scraping.kayak

import com.example.easyflight.flights.adapters.*
import com.example.easyflight.scraping.GenericSearchFlow
import com.example.easyflight.scraping.util.KayakCaptchaScript
import com.example.easyflight.scraping.util.UrlBuilder
import com.example.easyflight.scraping.util.UserAgentSelector
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.regex.Pattern


@Component
class KayakScraper(
        urlBuilder: UrlBuilder
) : GenericSearchFlow(urlBuilder) {

    private val LOGGER = LoggerFactory.getLogger(KayakScraper::class.java)



    override fun getKayakFLights(url: String): List<Result> {
        val response = Jsoup.connect(url).header("User-Agent", UserAgentSelector.getRandom()).execute()

        if(response.url().toString().contains("security")){
            KayakCaptchaScript.execute(response.url().toString())
             getKayakFLights(url)
        }
        val document = response.parse()

        val scripts = document.select("script")

        // Buscar el último script que sea de tipo "text/javascript"
        val script = scripts.last { it.attr("type").equals("text/javascript", ignoreCase = true) }

        return extractData(script)


    }


    private fun extractData(script: Element): List<Result> {
        // Obtiene el contenido del script
        val scriptContent = script.data()

        // Define un patrón para extraer la sección del JSON que necesitas
        val pattern = Pattern.compile("FlightResultsList\":(.*?),\"FlightFilterData")
        val matcher = pattern.matcher(scriptContent)

        // Verifica si el patrón coincide con el contenido del script
        if (matcher.find()) {
            // Extrae la sección del JSON que necesitas
            val json = matcher.group(1)
            // Convierte el string JSON en un objeto JSON en Kotlin
            val resultsJson = Gson().fromJson(json, JsonObject::class.java).getAsJsonObject("results")
            val resultList = mutableListOf<Result>()
            resultsJson.keySet()
                    .filter { key -> key.matches(Regex("[a-zA-Z0-9]{32}")) }
                    .forEach {
                        key -> resultList.add(getResult(resultsJson.getAsJsonObject(key))) }
            return resultList
        } else {
            LOGGER.error("No se encontró la sección del JSON que necesitas.")
        }
        return listOf()
    }

    private fun getResult(resultJson: JsonObject): Result {
        val resultId = resultJson.get("resultId").asString
        val legs = resultJson.getAsJsonArray("legs").map { legJson ->
            val legId = legJson.asJsonObject.get("legId").asString
            val duration = legJson.asJsonObject.get("legDurationDisplay").asString
            val flights = legJson.asJsonObject.getAsJsonArray("segments").map { segmentJson ->
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
                val layover = segmentJson.asJsonObject.get("layover")?.asJsonObject?.let {layoverJson ->
                    Layover(
                            duration = layoverJson.asJsonObject.get("duration").asString,
                            message = layoverJson.asJsonObject.get("message").asString
                    )
                }
                Flight(number, airline, departure, arrival, flightDuration, layover)
            }
            Leg(legId, flights, duration)
        }
        val options = resultJson.getAsJsonArray("optionsByFare").map { optionsByFareJson ->
            val options1 = optionsByFareJson.asJsonObject.getAsJsonArray("options").map {optionsJson ->
                val url = optionsJson.asJsonObject.get("url").asString
                val bookingId = optionsJson.asJsonObject.get("bookingId").asString
                val price = optionsJson.asJsonObject.get("displayPrice").asString
                val providerInfo = optionsJson.asJsonObject.get("providerInfo").let { providerInfoJson ->
                    val name = providerInfoJson.asJsonObject.get("displayName").asString
                    val logoUrl = providerInfoJson.asJsonObject.getAsJsonArray("logoUrls").map { logoUrlsJson ->
                        val providerUrl = logoUrlsJson.asJsonObject.get("image").asString
                        providerUrl
                    }[0]
                    ProviderInfo(name, logoUrl)
                }
                Option(url, bookingId, price, providerInfo)
            }
            options1
        }[0]

        return Result(resultId, legs, options)

    }
}




