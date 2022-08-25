package com.demo.bitcointradingsystem.dto.responseDto

import java.time.LocalDateTime

data class GetDayCandleV1Dto (
        val market: String,
        val candleDateTimeKst: LocalDateTime,
        val openingPrice: Double,
        val highPrice: Double,
        val lowPrice: Double,
        val tradePrice: Double,
        val candleAccTradeVolume: Double
)
