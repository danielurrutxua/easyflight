package com.example.easyflight.airports.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Airport (
    @Id
    val IATA: String,
    val name: String,
    val country: String
        )