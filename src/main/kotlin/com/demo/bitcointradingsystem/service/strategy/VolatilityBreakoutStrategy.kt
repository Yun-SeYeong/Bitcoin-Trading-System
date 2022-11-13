package com.demo.bitcointradingsystem.service.strategy

import com.demo.bitcointradingsystem.dto.strategyDto.VolatilityBreakoutBuyRequestDto
import com.demo.bitcointradingsystem.dto.strategyDto.VolatilityBreakoutSellRequestDto
import com.demo.bitcointradingsystem.entity.candle.DayCandle
import com.demo.bitcointradingsystem.entity.trading.TradingHistory
import com.demo.bitcointradingsystem.entity.trading.TradingType
import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.service.upbit.UpbitService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class VolatilityBreakoutStrategy(
    private val upbitService: UpbitService,
    private val dayCandleRepository: DayCandleRepository
) : Strategy<VolatilityBreakoutBuyRequestDto, VolatilityBreakoutSellRequestDto> {

    override fun buyStrategy(market: String, request: VolatilityBreakoutBuyRequestDto): Boolean {

        val candleNowList = upbitService.getCandlesDays("KRW-BTC", 1, "")

        if (candleNowList!!.size != 1) {
            return false
        }

        val weight = getWeight(market, LocalDateTime.now().minusDays(1), candleNowList[0], request)

        return weight < candleNowList[0].tradePrice
    }

    override fun sellStrategy(
        market: String,
        tradingHistory: TradingHistory,
        request: VolatilityBreakoutSellRequestDto
    ): Boolean {
        val nextOpen = tradingHistory.tradingDateTime.minusHours(9).plusDays(1)
        return LocalDateTime.now().minusHours(9).isAfter(nextOpen)
    }

    override fun backTesting(
        market: String,
        startDate: LocalDate,
        endDate: LocalDate,
        buyRequest: VolatilityBreakoutBuyRequestDto,
        sellRequest: VolatilityBreakoutSellRequestDto
    ): List<TradingHistory> {
        val tradingHistoryList = ArrayList<TradingHistory>()

        var start = startDate

        while (start.isBefore(endDate)) {
            val now = LocalDateTime.of(start, LocalTime.of(9, 0))

            val candleNow = dayCandleRepository.findByMarketAndDatetime(market, now.minusDays(1), now)

            if (tradingHistoryList.size > 0) {
                val lastTrading = tradingHistoryList[tradingHistoryList.size - 1]
                if (lastTrading.tradingType == TradingType.BUY) {
                    tradingHistoryList.add(
                        TradingHistory(
                            lastTrading.market,
                            now,
                            TradingType.SELL,
                            candleNow.openingPrice,
                            lastTrading.count
                        )
                    )
                }
            }

            val weight = getWeight(
                market,
                now,
                candleNow,
                buyRequest
            )

            println("weight = $weight")
            println("candleNow = $candleNow")

            if (candleNow.lowPrice < weight && candleNow.highPrice > weight) {
                tradingHistoryList.add(TradingHistory(market, now, TradingType.BUY, weight, 1))
            }

            start = start.plusDays(1)
        }

        return tradingHistoryList
    }

    private fun getWeight(
        market: String,
        end: LocalDateTime,
        candleNow: DayCandle,
        request: VolatilityBreakoutBuyRequestDto
    ): Double {
        val start = end.minusDays(1)

        val lastDayCandle = dayCandleRepository.findByMarketAndDatetime(market, start, end)

        return candleNow.openingPrice + ((lastDayCandle.highPrice - lastDayCandle.lowPrice) * request.k)
    }
}
