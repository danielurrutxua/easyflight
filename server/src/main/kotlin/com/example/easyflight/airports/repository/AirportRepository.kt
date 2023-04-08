package com.example.easyflight.airports.repository

import com.example.easyflight.airports.model.Airport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface AirportRepository: CrudRepository<Airport, String> {

    @Query("""
        SELECT * FROM airport
        WHERE LOWER(iata) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(country) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY
            CASE WHEN LOWER(name) LIKE LOWER(CONCAT(:query, '%')) THEN 0 ELSE 1 END,
            CASE WHEN LOWER(iata) LIKE LOWER(CONCAT(:query, '%')) THEN 0 ELSE 1 END,
            CASE WHEN LOWER(country) LIKE LOWER(CONCAT(:query, '%')) THEN 0 ELSE 1 END,
            name,
            iata,
            country
        LIMIT :limit
    """, nativeQuery = true)
    fun searchSuggested(@Param("query") query: String, @Param("limit") limit: Int): List<Airport>

}