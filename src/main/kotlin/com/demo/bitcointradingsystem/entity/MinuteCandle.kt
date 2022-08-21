package com.demo.bitcointradingsystem.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(MinuteCandleKey::class)
data class MinuteCandle(
        @Id
        val market: String,
        val candleDateTimeUtc: LocalDateTime,
        val candleDateTimeKst: LocalDateTime,
        val openingPrice: Double,
        val highPrice: Double,
        val lowPrice: Double,
        val tradePrice: Double,
        @Id
        val timestamp: Long,
        val candleAccTradePrice: Double,
        val candleAccTradeVolume: Double,
        val unit: Int
)
