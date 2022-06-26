package com.example.easyflight.airports.repository

import com.example.easyflight.airports.model.Airport
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface AirportRepository: CrudRepository<Airport, String> {

    @Query("SELECT a FROM Airport a WHERE a.name LIKE :search_text%")
    fun loadAirportsStartingWith(@Param("search_text") searchText: String): List<Airport>

    @Query("SELECT name FROM Airport WHERE IATA = (:iata)")
    fun getAirportName(iata: String): String
}