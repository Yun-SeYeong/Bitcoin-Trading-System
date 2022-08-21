package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.MarketCode
import org.springframework.data.jpa.repository.JpaRepository

interface MarketCodeRepository : JpaRepository<MarketCode, String> {
}