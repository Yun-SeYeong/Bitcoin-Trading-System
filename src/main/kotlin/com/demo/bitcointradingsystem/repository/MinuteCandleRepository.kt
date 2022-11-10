package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.candle.MinuteCandle
import com.demo.bitcointradingsystem.entity.candle.MinuteCandleKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MinuteCandleRepository : JpaRepository<MinuteCandle, MinuteCandleKey>{

    @Query("select mc from MinuteCandle mc where mc.minuteCandleKey.market = :market and mc.minuteCandleKey.unit = :unit order by mc.minuteCandleKey.timestamp desc ")
    fun findByMarketAndUnit(market: String, unit: Int): List<MinuteCandle>
}