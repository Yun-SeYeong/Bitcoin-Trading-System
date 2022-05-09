package com.demo.bitcointradingsystem

import com.demo.bitcointradingsystem.sync.SyncService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class SyncTest {
    @Autowired
    private lateinit var syncService: SyncService

    @Test
    @DisplayName(value = "Sync Minute Candle Test")
    fun SyncMinuteCandleTest() {
        //given
        val unit = 1
        val count = 10
        val market = "KRW-BTC"

        //when
        val syncMinuteCandle = syncService.syncMinuteCandle(unit, market, count)

        //then
        assertNotNull(syncMinuteCandle)
    }

    @Test
    @DisplayName(value = "Sync Day Candle Test")
    fun SyncDayCandleTest() {
        //given
        val count = 10
        val market = "KRW-BTC"

        //when
        val syncDayCandle = syncService.syncDayCandle(market, count)

        //then
        assertNotNull(syncDayCandle)
    }

    @Test
    @DisplayName(value = "Sync Day Candle Test")
    fun SyncLastDayCandleTest() {
        //given
        val market = "KRW-BTC"

        //when
        val syncDayCandle = syncService.syncLastDayCandle(market)

        println("syncDayCandle.candleDateTimeKst = ${syncDayCandle!!.candleDateTimeKst}")

        //then
        assertNotNull(syncDayCandle)
        assertEquals(syncDayCandle.candleDateTimeKst.year, LocalDateTime.now().year)
        assertEquals(syncDayCandle.candleDateTimeKst.month, LocalDateTime.now().month)
        assertEquals(syncDayCandle.candleDateTimeKst.dayOfMonth, LocalDateTime.now().dayOfMonth - 1)
    }

    @Test
    @DisplayName(value = "Sync Market Code Test")
    fun SyncMarketCodeTest() {
        //given
        val isDetails = true

        //when
        val syncMarketCode = syncService.syncMarketCode(isDetails)

        //then
        assertNotNull(syncMarketCode)
    }
}