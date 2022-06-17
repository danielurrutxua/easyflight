package com.example.easyflight.airports.service

import com.example.easyflight.airports.model.Airport

interface AirportInterface {

    fun loadToDb()
    fun searchStartingWith(searchText: String): List<String>
}
