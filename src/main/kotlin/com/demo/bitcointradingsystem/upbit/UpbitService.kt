package com.demo.bitcointradingsystem.upbit

import com.demo.bitcointradingsystem.dto.DayCandle
import com.demo.bitcointradingsystem.dto.MarketCode
import com.demo.bitcointradingsystem.dto.MinuteCandle

interface UpbitService {
    fun getMarketAll(isDetails: Boolean): List<MarketCode>?
    fun getCandlesMinutes(unit: Int, market: String, count: Int): List<MinuteCandle>?
    fun getCandlesDays(market: String, count: Int): List<DayCandle>?
}