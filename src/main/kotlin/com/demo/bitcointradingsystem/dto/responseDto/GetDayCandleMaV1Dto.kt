package com.demo.bitcointradingsystem.dto.responseDto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetDayCandleMaV1Dto(
        val market: String,
        val dateTimeKst: String,
        val ma5: Double?,
        val ma10: Double?,
        val ma15: Double?,
        val ma20: Double?,
        val ma60: Double?,
        val ma120: Double?
)