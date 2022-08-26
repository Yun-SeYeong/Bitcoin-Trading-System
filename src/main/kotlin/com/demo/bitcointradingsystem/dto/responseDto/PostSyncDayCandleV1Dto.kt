package com.demo.bitcointradingsystem.dto.responseDto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PostSyncDayCandleV1Dto(
        val syncCount: Int
)