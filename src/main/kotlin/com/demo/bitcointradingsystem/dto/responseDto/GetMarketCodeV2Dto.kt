package com.demo.bitcointradingsystem.dto.responseDto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetMarketCodeV2Dto(
        val marketCode: String,
        val koreanName: String,
        val englishName: String,
        val createdDate: LocalDateTime
)