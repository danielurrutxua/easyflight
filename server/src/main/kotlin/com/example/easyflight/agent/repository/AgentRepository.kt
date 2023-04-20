package com.example.easyflight.agent.repository

import com.example.easyflight.agent.model.Agent
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface AgentRepository: CrudRepository<Agent, Long> {

    @Query("select logoUrl from Agent where name = (:name)")
    fun findUrlByName(name: String): String?


}