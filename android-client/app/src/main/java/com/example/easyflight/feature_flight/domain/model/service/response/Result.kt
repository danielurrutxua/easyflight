package com.example.easyflight.feature_flight.domain.model.service.response

data class Result(
    val id: String,
    val legs: List<Leg>,
    val options: List<Option>,
    val score: Double?
)

data class Leg(
     val id: String,
     val segments: List<Segment>,
     val duration: String,
)

data class Layover(
     val duration: String,
     val message: String
)

data class Segment(
     val number: String,
     val airline: Airline,
     val departure: Departure,
     val arrival: Arrival,
     val duration: String,
     val layover: Layover?
)

data class Departure(
     val airport: Airport,
     val localDateTime: String,

    )

data class Arrival(
     val airport: Airport,
     val localDateTime: String
)

data class Airport(
     val code: String,
     val name: String
)

data class Airline(
     val code: String,
     val name: String,
     val logoUrl: String
)

data class Option(
     val url: String,
     val bookingId: String,
     val price: String,
     val agent: Agent
)

data class Agent(
     val name: String,
     val logoUrl: String
)