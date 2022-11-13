package com.demo.bitcointradingsystem.entity.trading

import java.time.LocalDateTime

class TradingHistory(
    market: String,
    tradeDateTime: LocalDateTime,
    tradingType: TradingType,
    price: Double,
    count: Long
) {

    var market: String = market
        private set

    var tradingDateTime: LocalDateTime = tradeDateTime
        private set

    var tradingType: TradingType = tradingType
        private set

    var price: Double = price
        private set

    var count: Long = count

    override fun toString(): String {
        return "TradingHistory(market='$market', tradingDateTime=$tradingDateTime, tradingType=$tradingType, price=$price, count=$count)"
    }
}