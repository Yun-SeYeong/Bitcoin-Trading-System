package com.demo.bitcointradingsystem.service.candle

import com.demo.bitcointradingsystem.dto.responseDto.*

interface CandleService {
    fun getMinuteCandle(market: String, unit: Int, count: Int): List<GetMinuteCandleV1Dto>

    fun getDayCandle(market: String, count: Int): List<GetDayCandleV1Dto>

    fun getMarketCodeV1(): List<GetMarketCodeV1Dto>
    fun getMarketCodeV2(): List<GetMarketCodeV2Dto>
    fun getDayCandleMa(market: String, count: Int): List<GetDayCandleMaV1Dto>
}