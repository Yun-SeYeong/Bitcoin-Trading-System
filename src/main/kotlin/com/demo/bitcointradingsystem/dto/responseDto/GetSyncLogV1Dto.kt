package com.demo.bitcointradingsystem.dto.responseDto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetSyncLogV1Dto(
        val jobId: String,
        val startDateTime: LocalDateTime,
        val endDateTime: LocalDateTime?,
        val status: String,
        val msg: String
)