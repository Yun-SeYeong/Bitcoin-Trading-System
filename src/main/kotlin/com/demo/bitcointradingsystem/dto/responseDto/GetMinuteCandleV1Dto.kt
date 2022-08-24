package com.demo.bitcointradingsystem.dto.responseDto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetMinuteCandleV1Dto(
        val market: String,
        val candleDateTimeKst: LocalDateTime,
        val openingPrice: Double,
        val highPrice: Double,
        val lowPrice: Double,
        val tradePrice: Double,
        val candleAccTradeVolume: Double
)