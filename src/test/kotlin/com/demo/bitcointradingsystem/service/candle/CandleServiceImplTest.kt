package com.demo.bitcointradingsystem.service.candle

import com.demo.bitcointradingsystem.service.sync.SyncService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.PostConstruct

@SpringBootTest
internal class CandleServiceImplTest {

    @Autowired
    private lateinit var candleService: CandleService

    @Autowired
    private lateinit var syncService: SyncService

    @PostConstruct
    fun init() {
        syncService.syncMinuteCandle(3, "KRW-BTC", 100)
        syncService.syncDayCandle("KRW-BTC", 100)
    }

    @Test
    fun getMinuteCandleTest() {
        // given
        val unit = 3
        val count = 10
        val market = "KRW-BTC"

        // when
        val minuteCandles = candleService.getMinuteCandle(market, unit, count)

        // then
        assertThat(minuteCandles.size).isEqualTo(count)
    }

    @Test
    fun getDayCandleTest() {
        // given
        val count = 10
        val market = "KRW-BTC"

        // when
        val dayCandles = candleService.getDayCandle(market, count)

        //then
        assertThat(dayCandles.size).isEqualTo(count)

    }

    @Test
    fun getMarketCodeTest() {
        //when
        val marketCodes = candleService.getMarketCodeV2()

        println("marketCodes = ${marketCodes}")
        println("marketCodes.sortedBy { it.createdDate } = ${marketCodes.sortedBy { it.createdDate }}")

        //then
        assertThat(marketCodes).isEqualTo(marketCodes.sortedBy { it.createdDate })
    }

}