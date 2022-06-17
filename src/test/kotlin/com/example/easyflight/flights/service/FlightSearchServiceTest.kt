package com.example.easyflight.flights.service

import com.example.easyflight.flights.adapters.FlightSearchRequest
import com.example.easyflight.flights.adapters.FlightSearchResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class FlightSearchServiceTest {

    private val flightSearchService: FlightSearchService = mockk()

    @Test
    fun whenGetBankAccount_thenReturnBankAccount() {
//        //given
//        val flightSearchRequest = FlightSearchRequest(
//            "BIO",
//            "ROM",
//            LocalDate.of(2022, Month.APRIL, 15),
//            LocalDate.of(2022, Month.APRIL, 22),
//            3,
//            1
//        )
//
//        every { flightSearchService.search(flightSearchRequest) } returns FlightSearchResponse(flightSearchRequest, listOf());
//
//        //when
//        //val result = bankAccountService.getBankAccount(1);
//
//        //then
//       // assertEquals(bankAccount, result)
    }
}