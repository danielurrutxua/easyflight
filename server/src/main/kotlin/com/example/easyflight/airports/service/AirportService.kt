package com.example.easyflight.airports.service

import com.example.easyflight.airports.exceptions.AirportNotFoundException
import com.example.easyflight.airports.model.Airport
import com.example.easyflight.airports.repository.AirportRepository
import com.example.easyflight.scraping.drivers.chrome.ChromeDriverInitializer
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class AirportService(
    private val airportRepository: AirportRepository,
    private val driverInitializer: ChromeDriverInitializer
) : AirportInterface {

    private val LOGGER = LoggerFactory.getLogger(AirportService::class.java)

    @Value("\${url.airports.code.list}")
    private lateinit var url: String
    override fun loadToDb() {

        LOGGER.info("Load Airports into DB: IN")

        val driver = driverInitializer.initialize(url)
        Jsoup.parse(driver.pageSource).getElementsByTag("tbody").forEach { element ->
            element.getElementsByTag("tr").forEach { element1 ->
                val info = element1.getElementsByTag("td")
                if (info.size == 3) {
                    airportRepository.save(Airport(info[2].text(), info[0].text(), info[1].text()))
                }
            }

        }
        driver.close()

        LOGGER.info("Load Airports into DB: OUT")

    }
    override fun searchStartingWith(searchText: String) = airportRepository.loadAirportsStartingWith(searchText)
    override fun getAirportName(iata: String): String {

        try {
            return iata + " " + airportRepository.getAirportName(iata)
        } catch (ex: Exception) {
            throw AirportNotFoundException("Airport not found, IATA: $iata")
        }
    }
    override fun findById(iata: String) = airportRepository.findById(iata)
}