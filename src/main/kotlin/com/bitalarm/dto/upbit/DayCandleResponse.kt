package com.bitalarm.dto.upbit

import java.time.LocalDateTime

data class DayCandleResponse (
    val market: String,
    val candleDateTimeUtc: LocalDateTime,
    val candleDateTimeKst: LocalDateTime,
    val openingPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val tradePrice: Double,
    val timestamp: Long,
    val candleAccTradePrice: Double,
    val candleAccTradeVolume: Double,
    val prevClosingPrice: Double,
    val changePrice: Double,
    val changeRate: Double,
    val convertedTradePrice: Double
)