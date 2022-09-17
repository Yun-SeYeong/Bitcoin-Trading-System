package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.Scenario
import org.springframework.data.jpa.repository.JpaRepository

interface ScenarioRepository : JpaRepository<Scenario, Long>{
}