package com.bitalarm.dto.upbit

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MarketCodeResponse (
    val market:String,
    val koreanName:String,
    val englishName:String,
    val marketWarning: String?
)