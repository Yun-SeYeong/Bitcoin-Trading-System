package com.demo.bitcointradingsystem.service.strategy

import com.demo.bitcointradingsystem.entity.trading.TradingHistory
import java.time.LocalDate

interface Strategy<T, K> {

    fun buyStrategy(market: String, request: T): Boolean

    fun sellStrategy(market: String, tradingHistory: TradingHistory, request: K): Boolean

    fun backTesting(
        market: String,
        startDate: LocalDate,
        endDate: LocalDate,
        buyRequest: T,
        sellRequest: K
    ): List<TradingHistory>
}