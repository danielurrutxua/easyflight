package com.example.easyflight.airline.repository

import com.example.easyflight.airports.model.Airline
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface AirlineRepository: CrudRepository<Airline, Long> {

    @Query("select logoUrl from Airline where name = (:name)")
    fun findUrlByName(name: String): String?


}