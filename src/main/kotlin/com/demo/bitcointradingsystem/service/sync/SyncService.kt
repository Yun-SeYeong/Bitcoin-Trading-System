package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.MarketCode
import com.demo.bitcointradingsystem.entity.MinuteCandle

interface SyncService {
    fun syncMinuteCandle(unit: Int, market: String, count: Int) : List<MinuteCandle>
    fun syncMinuteCandleWithDate(unit: Int, market: String, count: Int, to: String): List<MinuteCandle>
    fun batchMinuteCandleWithDate(unit: Int, market: String, count: Int, to: String): Int
    fun syncDayCandle(market: String, count: Int): List<DayCandle>
    fun syncDayCandleWithDate(market: String, count: Int, to: String): List<DayCandle>
    fun batchDayCandleWithDate(market: String, count: Int, to: String): Int
    fun syncLastDayCandle(market: String): DayCandle
    fun syncMarketCode(isDetails: Boolean): List<MarketCode>
    fun syncDayCandleMacd(market: String): Int
    fun syncMinuteCandleMacd(market: String, unit: Int): Int
}