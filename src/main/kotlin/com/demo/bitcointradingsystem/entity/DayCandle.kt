package com.demo.bitcointradingsystem.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(DayCandleKey::class)
class DayCandle(
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
        prevClosingPrice: Double,
        changePrice: Double,
        changeRate: Double,
        convertedTradePrice: Double
) {
        @Id
        var market: String = market
                protected set
        @Id
        var timestamp: Long = timestamp
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
        var prevClosingPrice: Double = prevClosingPrice
                protected set
        var changePrice: Double = changePrice
                protected set
        var changeRate: Double = changeRate
                protected set
        var convertedTradePrice: Double = convertedTradePrice
                protected set

        override fun toString(): String {
                return "DayCandle(market='$market', timestamp=$timestamp, candleDateTimeUtc=$candleDateTimeUtc, candleDateTimeKst=$candleDateTimeKst, openingPrice=$openingPrice, highPrice=$highPrice, lowPrice=$lowPrice, tradePrice=$tradePrice, candleAccTradePrice=$candleAccTradePrice, candleAccTradeVolume=$candleAccTradeVolume, prevClosingPrice=$prevClosingPrice, changePrice=$changePrice, changeRate=$changeRate, convertedTradePrice=$convertedTradePrice)"
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as DayCandle

                if (market != other.market) return false
                if (timestamp != other.timestamp) return false

                return true
        }

        override fun hashCode(): Int {
                var result = market.hashCode()
                result = 31 * result + timestamp.hashCode()
                return result
        }


}
