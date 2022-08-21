package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.MinuteCandle
import com.demo.bitcointradingsystem.entity.MinuteCandleKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface MinuteCandleRepository : JpaRepository<MinuteCandle, MinuteCandleKey>{

    fun findByMarketAndUnit(market: String, unit: Int): List<MinuteCandle>
}