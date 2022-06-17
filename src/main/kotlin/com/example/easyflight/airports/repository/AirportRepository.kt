package com.example.easyflight.airports.repository

import com.example.easyflight.airports.model.Airport
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface AirportRepository: CrudRepository<Airport, Long> {

    @Query("SELECT name FROM Airport WHERE name LIKE :search_text%")
    fun loadAirportsStartingWith(@Param("search_text") searchText: String): List<String>
}