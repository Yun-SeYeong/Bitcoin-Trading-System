package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.dto.MarketCode
import org.springframework.data.jpa.repository.JpaRepository

interface MarketCodeRepository : JpaRepository<MarketCode, String> {
}