package com.example.easyflight.flights.adapters.response

data class Result(
        private val id: String,
        private val legs: List<Leg>,
        private val options: List<Option>,
        val score: Double?
)

data class Leg(
        private val id: String,
        private val segments: List<Segment>,
        private val duration: String,
)

data class Layover(
        private val duration: String,
        private val message: String
)

data class Segment(
        private val number: String,
        private val airline: Airline,
        private val departure: Departure,
        private val arrival: Arrival,
        private val duration: String,
        private val layover: Layover?
)

data class Departure(
        private val airport: Airport,
        private val localDateTime: String,

        )

data class Arrival(
        private val airport: Airport,
        private val localDateTime: String
)

data class Airport(
        private val code: String,
        private val name: String
)

data class Airline(
        private val code: String,
        private val name: String,
        private val logoUrl: String
)

data class Option(
        private val url: String,
        private val bookingId: String,
        private val price: String?,
        private val agent: Agent
)

data class Agent(
        private val name: String,
        private val logoUrl: String
)

