package com.example.easyflight.flights.service.json.services

import com.example.easyflight.flights.adapters.response.Result
import com.google.gson.JsonObject

interface JsonParser {
    fun execute(resultJson: JsonObject): List<Result>

}
