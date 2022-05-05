package com.demo.bitcointradingsystem

import com.demo.bitcointradingsystem.sync.SyncService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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
}