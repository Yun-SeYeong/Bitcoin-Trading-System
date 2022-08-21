package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.DayCandleKey
import org.springframework.data.jpa.repository.JpaRepository

interface DayCandleRepository : JpaRepository<DayCandle, DayCandleKey>{
    fun findByMarket(market: String): List<DayCandle>
}