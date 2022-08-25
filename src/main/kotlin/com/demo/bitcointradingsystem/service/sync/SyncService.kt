package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.MarketCode
import com.demo.bitcointradingsystem.entity.MinuteCandle

interface SyncService {
    fun syncMinuteCandle(unit: Int, market: String, count: Int) : List<MinuteCandle>
    fun syncMinuteCandleWithDate(unit: Int, market: String, count: Int, to: String): List<MinuteCandle>
    fun syncDayCandle(market: String, count: Int): List<DayCandle>
    fun syncLastDayCandle(market: String): DayCandle
    fun syncMarketCode(isDetails: Boolean): List<MarketCode>
}