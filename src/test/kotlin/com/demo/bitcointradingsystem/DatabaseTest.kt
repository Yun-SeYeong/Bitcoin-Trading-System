package com.demo.bitcointradingsystem

import com.demo.bitcointradingsystem.entity.*
import com.demo.bitcointradingsystem.repository.DayCandleAnalysisRepository
import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime

@SpringBootTest
@Transactional
class DatabaseTest {

    @Autowired
    private lateinit var minuteCandleRepository: MinuteCandleRepository

    @Autowired
    private lateinit var dayCandleRepository: DayCandleRepository

    @Autowired
    private lateinit var marketCodeRepository: MarketCodeRepository

    @Autowired
    private lateinit var dayCandleAnalysisRepository: DayCandleAnalysisRepository

    @Test
    @DisplayName("MinuteCandle 저장")
    fun saveMinuteCandle() {
        // given

        /**
         * MinuteCandle(market=KRW-BTC, candleDateTimeUtc=2022-04-30T06:15, candleDateTimeKst=2022-04-30T15:15, openingPrice=4.9763E7, highPrice=4.9774E7, lowPrice=4.9738E7, tradePrice=4.977E7, timestamp=1651299347948, candleAccTradePrice=1.5796587238106E8, candleAccTradeVolume=3.1743735, unit=1)
         */
        val minuteCandle = MinuteCandle(
                market="KRW-BTC",
                candleDateTimeUtc=LocalDateTime.parse("2022-04-30T06:15"),
                candleDateTimeKst=LocalDateTime.parse("2022-04-30T15:15"),
                openingPrice=4.9763E7,
                highPrice=4.9774E7,
                lowPrice=4.9738E7,
                tradePrice=4.977E7,
                timestamp=1651299347948,
                candleAccTradePrice=1.5796587238106E8,
                candleAccTradeVolume=3.1743735,
                unit=1
        )

        // when
        val savedMinuteCandle = minuteCandleRepository.save(minuteCandle)
        val findMinuteCandle = minuteCandleRepository.findById(MinuteCandleKey(minuteCandle.market, minuteCandle.timestamp)).get()

        // then
        assertThat(findMinuteCandle).isEqualTo(savedMinuteCandle)
        assertThat(savedMinuteCandle.hashCode()).isEqualTo(savedMinuteCandle.hashCode())
    }

    @Test
    @DisplayName("DayCandle 저장")
    fun saveDayCandle() {
        // given

        /**
         *  DayCandle(market=KRW-BTC, candleDateTimeUtc=2022-04-22T00:00, candleDateTimeKst=2022-04-22T09:00, openingPrice=5.0805E7, highPrice=5.1E7, lowPrice=4.9526E7, tradePrice=4.9937E7, timestamp=1650671999479, candleAccTradePrice=1.6651841733012607E11, candleAccTradeVolume=3309.37626968, prevClosingPrice=5.0805E7, changePrice=-868000.0, changeRate=-0.0170849326, convertedTradePrice=0.0)
         */
        val dayCandle = DayCandle(
                market="KRW-BTC",
                candleDateTimeUtc=LocalDateTime.parse("2022-04-22T00:00"),
                candleDateTimeKst=LocalDateTime.parse("2022-04-22T09:00"),
                openingPrice=5.0805E7,
                highPrice=5.1E7,
                lowPrice=4.9526E7,
                tradePrice=4.9937E7,
                timestamp=1650671999479,
                candleAccTradePrice=1.6651841733012607E11,
                candleAccTradeVolume=3309.37626968,
                prevClosingPrice=5.0805E7,
                changePrice=-868000.0,
                changeRate=-0.0170849326,
                convertedTradePrice=0.0
        )

        // when
        val savedDayCandle = dayCandleRepository.save(dayCandle)
        val findDayCandle = dayCandleRepository.findById(CandleKey(savedDayCandle.candleKey.market, savedDayCandle.candleKey.timestamp)).get()

        // then
        assertThat(savedDayCandle).isEqualTo(findDayCandle)
        assertThat(savedDayCandle.hashCode()).isEqualTo(findDayCandle.hashCode())
    }

    @Test
    @DisplayName("MarketCode 저장")
    fun saveMarketCode() {
        // given

        /**
         *  MarketCode(market=KRW-BTC, koreanName=비트코인, englishName=Bitcoin, marketWarning=null)
         */
        val marketCode = MarketCode(market="KRW-BTC", koreanName="비트코인", englishName="Bitcoin", marketWarning=null)

        // when
        val savedMarketCode = marketCodeRepository.save(marketCode)
        val findMarketCode = marketCodeRepository.findById(marketCode.market).get()

        // then
        assertThat(savedMarketCode).isEqualTo(findMarketCode)
        assertThat(savedMarketCode.hashCode()).isEqualTo(findMarketCode.hashCode())
    }

    @Test
    @DisplayName("DayCandleAnalysis 저장")
    fun saveDayCandleAnalysis() {
        // given
        val marketCode = DayCandleMacd(CandleKey("KRW-BTC", Timestamp.valueOf(LocalDateTime.now()).time), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        // when
        val savedMarketCode = dayCandleAnalysisRepository.save(marketCode)
        val findMarketCode = dayCandleAnalysisRepository.findById(savedMarketCode.candleKey).get()

        println("savedMarketCode = ${savedMarketCode}")

        // then
        assertThat(savedMarketCode).isEqualTo(findMarketCode)
        assertThat(savedMarketCode.hashCode()).isEqualTo(findMarketCode.hashCode())
    }

}