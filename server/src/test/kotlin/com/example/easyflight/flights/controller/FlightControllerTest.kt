package com.example.easyflight.flights.controller

import com.example.easyflight.flights.adapters.request.FlightSearchRequest
import com.example.easyflight.flights.service.FlightSearchService
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
class FlightControllerTest(@Autowired val mockMvc: MockMvc) {

    private val flightSearchService = mockk<FlightSearchService>()


    @Test
    fun givenInternetConnection_whenGetRequest_thenReturnsFlightSearchResponseJsonWithStatus200() {

        val flightSearchRequest = FlightSearchRequest(
            "BIO",
            "ROM",
            "23.05.2022",
            "23.06.2022",
            2,
            1
        )
        val flightSearchResponse = FlightSearchResponse(flightSearchRequest, listOf())
        every { flightSearchService.performSearch(flightSearchRequest) } returns flightSearchResponse

        mockMvc.perform(get("/flight/search?origin=BIO&destination=ROM&departure_date=23.05.2022&arrival_date=23.06.2022&adults=2&children=1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(Gson().toJson(flightSearchResponse)))
    }
}