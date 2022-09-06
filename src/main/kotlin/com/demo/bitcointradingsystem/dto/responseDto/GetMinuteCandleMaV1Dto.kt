package com.demo.bitcointradingsystem.dto.responseDto

data class GetMinuteCandleMaV1Dto(
        val market: String,
        val dateTimeKst: String,
        val ma5: Double?,
        val ma10: Double?,
        val ma15: Double?,
        val ma20: Double?,
        val ma60: Double?,
        val ma120: Double?
)