package com.example.easyflight.feature_flight.presentation.flights.components.result.adapters

data class FlightMainInfo(val leg1: LegMainInfo, val leg2: LegMainInfo, val airlines: List<String>, val price: String, val morePrices: Boolean)