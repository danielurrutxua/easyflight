package com.example.easyflight.agent.model

import javax.persistence.*

@Entity
data class Agent (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(length = 100, unique = true, nullable = false)
        val name: String,

        @Column(length = 500, nullable = false)
        val logoUrl: String
)