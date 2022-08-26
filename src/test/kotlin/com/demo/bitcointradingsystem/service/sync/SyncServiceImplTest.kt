package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
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

    @Test
    @DisplayName(value = "Sync Minute Candle Test")
    fun syncMinuteCandleTest() {
        //given
        val unit = 1
        val count = 10
        val market = "KRW-BTC"

        //when
        val syncMinuteCandle = syncService.syncMinuteCandle(unit, market, count)
        val findMinuteCandle = minuteCandleRepository.findByMarketAndUnit(market, unit)

        //then
        assertThat(syncMinuteCandle.size).isEqualTo(findMinuteCandle.size)
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
        dayCandleRepository.deleteAll()
        val syncDayCandle = syncService.syncDayCandle(market, count)
        val findMinuteCandle = dayCandleRepository.findByMarket(market)

        //then
        assertThat(syncDayCandle.size).isEqualTo(findMinuteCandle.size)
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
}