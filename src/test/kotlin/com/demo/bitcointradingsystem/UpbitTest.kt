package com.demo.bitcointradingsystem

import com.demo.bitcointradingsystem.upbit.UpbitService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UpbitTest {

    @Autowired
    private lateinit var upbitService: UpbitService

    @Test
    @DisplayName("Upbit API를 통해 마켓코드 조회")
    fun getMarketAll() {
        // given
        val isDetails = false

        // when
        val marketCodeArray = upbitService.getMarketAll(isDetails)

        // then
        Assertions.assertThat(marketCodeArray!!.size).isGreaterThan(0)
    }

    @Test
    @DisplayName("Upbit API를 통해 분봉 데이터 조회")
    fun getCandlesMinutes() {
        // given
        val unit = 1
        val count = 10
        val market = "KRW-BTC"

        // when
        val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count)

        // then
        assertEquals(minuteCandleArray!!.size, count)
    }

    @Test
    @DisplayName("Upbit API를 통해 일봉 데이터 조회")
    fun getCandlesDays() {
        // given
        val count = 10
        val market = "KRW-BTC"

        // when
        val dayCandleArray = upbitService.getCandlesDays(market, count)

        // then
        assertEquals(dayCandleArray!!.size, count)
    }
}