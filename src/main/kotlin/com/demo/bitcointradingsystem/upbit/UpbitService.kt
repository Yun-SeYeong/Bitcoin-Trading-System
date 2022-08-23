package com.demo.bitcointradingsystem.upbit

import com.demo.bitcointradingsystem.dto.Balance
import com.demo.bitcointradingsystem.dto.Order
import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.MarketCode
import com.demo.bitcointradingsystem.entity.MinuteCandle

interface UpbitService {
    fun getMarketAll(isDetails: Boolean): List<MarketCode>?
    fun getCandlesMinutes(unit: Int, market: String, count: Int): List<MinuteCandle>?
    fun getCandlesDays(market: String, count: Int): List<DayCandle>?
    fun getAccounts(authorization: String): List<Balance>?
    fun postOrders(authorization: String, market: String, side: String, volume: String, price: String, ordType: String): Order?
}