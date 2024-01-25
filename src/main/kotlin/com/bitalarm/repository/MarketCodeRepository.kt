package com.bitalarm.repository

import com.bitalarm.domain.MarketCode
import org.springframework.data.jpa.repository.JpaRepository

interface MarketCodeRepository : JpaRepository<MarketCode, String>