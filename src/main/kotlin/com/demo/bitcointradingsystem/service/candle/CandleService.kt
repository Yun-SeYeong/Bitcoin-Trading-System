package com.demo.bitcointradingsystem.service.candle

import com.demo.bitcointradingsystem.dto.responseDto.GetDayCandleV1Dto
import com.demo.bitcointradingsystem.dto.responseDto.GetMinuteCandleV1Dto

interface CandleService {
    fun getMinuteCandle(market: String, unit: Int, count: Int): List<GetMinuteCandleV1Dto>

    fun getDayCandle(market: String, count: Int): List<GetDayCandleV1Dto>
}