package com.example.easyflight.airports.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Airport (
        @Id
        @Column(length = 3)
        val iata: String,

        @Column(length = 100)
        val name: String,

        @Column(length = 50)
        val country: String
)