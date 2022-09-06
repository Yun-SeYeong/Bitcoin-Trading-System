package com.demo.bitcointradingsystem.entity

import java.time.LocalDateTime
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class MinuteCandle(
        market: String,
        candleDateTimeUtc: LocalDateTime,
        candleDateTimeKst: LocalDateTime,
        openingPrice: Double,
        highPrice: Double,
        lowPrice: Double,
        tradePrice: Double,
        timestamp: Long,
        candleAccTradePrice: Double,
        candleAccTradeVolume: Double,
        unit: Int
) {
    @EmbeddedId
    var minuteCandleKey: MinuteCandleKey = MinuteCandleKey(market, timestamp, unit)
        protected set
    var candleDateTimeUtc: LocalDateTime = candleDateTimeUtc
        protected set
    var candleDateTimeKst: LocalDateTime = candleDateTimeKst
        protected set
    var openingPrice: Double = openingPrice
        protected set
    var highPrice: Double = highPrice
        protected set
    var lowPrice: Double = lowPrice
        protected set
    var tradePrice: Double = tradePrice
        protected set
    var candleAccTradePrice: Double = candleAccTradePrice
        protected set
    var candleAccTradeVolume: Double = candleAccTradeVolume
        protected set

    override fun toString(): String {
        return "MinuteCandle(minuteCandleKey=$minuteCandleKey, candleDateTimeUtc=$candleDateTimeUtc, candleDateTimeKst=$candleDateTimeKst, openingPrice=$openingPrice, highPrice=$highPrice, lowPrice=$lowPrice, tradePrice=$tradePrice, candleAccTradePrice=$candleAccTradePrice, candleAccTradeVolume=$candleAccTradeVolume)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MinuteCandle

        if (minuteCandleKey != other.minuteCandleKey) return false

        return true
    }

    override fun hashCode(): Int {
        return minuteCandleKey.hashCode()
    }


}
