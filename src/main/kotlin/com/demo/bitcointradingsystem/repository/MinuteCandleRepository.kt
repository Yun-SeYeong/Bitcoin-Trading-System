package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.MinuteCandle
import com.demo.bitcointradingsystem.entity.MinuteCandleKey
import org.springframework.data.jpa.repository.JpaRepository

interface MinuteCandleRepository : JpaRepository<MinuteCandle, MinuteCandleKey>{

    fun findByMarketAndUnitOrderByTimestampDesc(market: String, unit: Int): List<MinuteCandle>
}