package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.repository.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct

@SpringBootTest
@Transactional
internal class SyncServiceImplTest {
    @Autowired
    private lateinit var syncService: SyncService

    @Autowired
    private lateinit var minuteCandleRepository: MinuteCandleRepository

    @Autowired
    private lateinit var dayCandleRepository: DayCandleRepository

    @Autowired
    private lateinit var marketCodeRepository: MarketCodeRepository

    @Autowired
    private lateinit var dayCandleAnalysisRepository: DayCandleAnalysisRepository

    @Autowired
    private lateinit var minuteCandleAnalysisRepository: MinuteCandleAnalysisRepository

    @PostConstruct
    fun init() {
        minuteCandleRepository.deleteAll()
        dayCandleRepository.deleteAll()
        marketCodeRepository.deleteAll()
    }

    @Test
    @DisplayName(value = "Sync Minute Candle Test")
    fun syncMinuteCandleTest() {
        //given
        val unit = 1
        val count = 200
        val market = "KRW-BTC"

        //when
        val syncMinuteCandle = syncService.syncMinuteCandle(unit, market, count)
        val findMinuteCandle = minuteCandleRepository.findByMarketAndUnit(market, unit)

        //then
        assertThat(syncMinuteCandle.size).isEqualTo(findMinuteCandle.size)
    }

    @Test
    @DisplayName(value = "Batch Minute Candle Test")
    fun batchMinuteCandleTest() {
        //given
        val unit = 1
        val count = 200
        val market = "KRW-BTC"

        //when
        val resultSize = syncService.batchMinuteCandleWithDate(unit, market, count, "")
        val findMinuteCandle = minuteCandleRepository.findByMarketAndUnit(market, unit)

        //then
        assertThat(resultSize).isEqualTo(findMinuteCandle.size)
    }

    @Test
    @DisplayName(value = "Sync Minute Candle With Specific Date Test")
    fun syncMinuteCandleWithDateTest() {
        //given
        val unit = 1
        val count = 10
        val market = "KRW-BTC"
        val year = 2021
        val month = 8
        val day = 25
        val to = "${year}-${String.format("%02d", month)}-${day}T00:00:00Z"

        //when
        val syncMinuteCandle = syncService.syncMinuteCandleWithDate(unit, market, count, to)
        val findMinuteCandle = minuteCandleRepository.findByMarketAndUnit(market, unit)

        //then
        assertThat(syncMinuteCandle.size).isEqualTo(findMinuteCandle.size)
        assertThat(LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")).minusMinutes(1))
                .isEqualTo(syncMinuteCandle[0].candleDateTimeUtc)
    }

    @Test
    @DisplayName(value = "Sync Day Candle With Specific Date Test")
    fun syncDayCandleWithDateTest() {
        //given
        val count = 10
        val market = "KRW-BTC"
        val year = 2021
        val month = 8
        val day = 25
        val to = "${year}-${String.format("%02d", month)}-${day}T00:00:00Z"

        //when
        dayCandleRepository.deleteAll()
        val syncDayCandle = syncService.syncDayCandleWithDate(market, count, to)
        val findDayCandle = dayCandleRepository.findByMarket(market)

        //then
        assertThat(syncDayCandle.size).isEqualTo(findDayCandle.size)
        assertThat(LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")).minusDays(1))
                .isEqualTo(syncDayCandle[0].candleDateTimeUtc)
    }

    @Test
    @DisplayName(value = "Sync Day Candle Test")
    fun syncDayCandleTest() {
        //given
        val count = 10
        val market = "KRW-BTC"

        //when
        val syncDayCandle = syncService.syncDayCandle(market, count)
        val findMinuteCandle = dayCandleRepository.findByMarket(market)

        //then
        assertThat(syncDayCandle.size).isEqualTo(findMinuteCandle.size)
    }

    @Test
    @DisplayName(value = "Batch Day Candle Test")
    fun batchDayCandleTest() {
        //given
        val count = 200
        val market = "KRW-BTC"

        //when
        val resultSize = syncService.batchDayCandleWithDate(market, count, "")
        val findMinuteCandle = dayCandleRepository.findByMarket(market)

        //then
        assertThat(resultSize).isEqualTo(findMinuteCandle.size)
    }

    @Test
    @DisplayName(value = "Sync Day Candle Test")
    fun syncLastDayCandleTest() {
        //given
        val market = "KRW-BTC"

        //when
        val syncDayCandle = syncService.syncLastDayCandle(market)

        //then
        assertNotNull(syncDayCandle)
        assertEquals(syncDayCandle.candleDateTimeKst.year, LocalDateTime.now().year)
        assertEquals(syncDayCandle.candleDateTimeKst.month, LocalDateTime.now().month)
        assertEquals(syncDayCandle.candleDateTimeKst.dayOfMonth, LocalDateTime.now().dayOfMonth - 1)
    }

    @Test
    @DisplayName(value = "Sync Market Code Test")
    fun syncMarketCodeTest() {
        //given
        val isDetails = true

        //when
        val syncMarketCode = syncService.syncMarketCode(isDetails)
        val findMarketcode = marketCodeRepository.findAll()

        //then
        assertThat(syncMarketCode.size).isEqualTo(findMarketcode.size)
    }

    @Test
    fun analysisDayCandleMacd() {
        //given
        val market = "KRW-BTC"
        val count = 200
        syncService.batchDayCandleWithDate(market, count, "")

        //when
        val findByMarket = dayCandleAnalysisRepository.findByMarket(market)
        val tradeList = dayCandleRepository.findByMarket(market).map { it.tradePrice }

        assertThat(findByMarket[0].ma5).isEqualTo(tradeList.take(5).average())
        assertThat(findByMarket[0].ma10).isEqualTo(tradeList.take(10).average())
        assertThat(findByMarket[0].ma15).isEqualTo(tradeList.take(15).average())
        assertThat(findByMarket[0].ma20).isEqualTo(tradeList.take(20).average())
        assertThat(findByMarket[0].ma60).isEqualTo(tradeList.take(60).average())
        assertThat(findByMarket[0].ma120).isEqualTo(tradeList.take(120).average())
    }

    @Test
    fun analysisMinuteCandleMacd() {
        // given
        val market = "KRW-BTC"
        val unit = 3
        val count = 200
        syncService.batchMinuteCandleWithDate(unit, market, count, "")

        // when
        val findByMarketAndUnit = minuteCandleAnalysisRepository.findByMarketAndUnit(market, unit)

        val tradeList = minuteCandleRepository.findByMarketAndUnit(market, unit).map { it.tradePrice }

        assertThat(findByMarketAndUnit[0].ma5).isEqualTo(tradeList.take(5).average())
        assertThat(findByMarketAndUnit[0].ma15).isEqualTo(tradeList.take(15).average())
        assertThat(findByMarketAndUnit[0].ma20).isEqualTo(tradeList.take(20).average())
        assertThat(findByMarketAndUnit[0].ma60).isEqualTo(tradeList.take(60).average())
        assertThat(findByMarketAndUnit[0].ma120).isEqualTo(tradeList.take(120).average())
    }

}