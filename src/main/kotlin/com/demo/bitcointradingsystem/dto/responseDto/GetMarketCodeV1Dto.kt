package com.demo.bitcointradingsystem.dto.responseDto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetMarketCodeV1Dto(
        val marketCode: String,
        val koreanName: String,
        val englishName: String
)