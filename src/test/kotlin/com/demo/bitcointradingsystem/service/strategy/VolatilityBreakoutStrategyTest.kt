package com.demo.bitcointradingsystem.service.strategy

import com.demo.bitcointradingsystem.dto.strategyDto.VolatilityBreakoutBuyRequestDto
import com.demo.bitcointradingsystem.dto.strategyDto.VolatilityBreakoutSellRequestDto
import com.demo.bitcointradingsystem.entity.trading.TradingHistory
import com.demo.bitcointradingsystem.entity.trading.TradingType
import com.demo.bitcointradingsystem.service.sync.SyncService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@SpringBootTest
@Transactional
class VolatilityBreakoutStrategyTest {

    @Autowired
    private lateinit var volatilityBreakoutStrategy: VolatilityBreakoutStrategy

    @Autowired
    private lateinit var syncService: SyncService

    private val TEST_MARKET = "KRW-POLY"

    @PostConstruct
    fun init() {
        syncService.syncDayCandle(TEST_MARKET, 100)
    }

    @Test
    fun buyStrategy() {
        // given
        val market = TEST_MARKET
        val requestDto = VolatilityBreakoutBuyRequestDto(0.5)

        //when
        val buyStrategy = volatilityBreakoutStrategy.buyStrategy(market, requestDto)

        //then
        assertThat(buyStrategy).isNotNull
    }

    @Test
    fun sellStrategy() {
        // given
        val market = TEST_MARKET
        val requestDto = VolatilityBreakoutSellRequestDto()
        val tradingHistory = TradingHistory(market, LocalDateTime.now(), TradingType.BUY, 1.0, 1.0)

        //when
        val sellStrategy = volatilityBreakoutStrategy.sellStrategy(market, tradingHistory, requestDto)
        println("sellStrategy = ${sellStrategy}")

        //then
        assertThat(sellStrategy).isNotNull
    }

    @Test
    fun backTesting() {
        val market = TEST_MARKET
        val start = LocalDate.of(2022, 10, 1)
        val end = LocalDate.of(2022, 10, 31)
        val buyRequestDto = VolatilityBreakoutBuyRequestDto(0.5)
        val sellRequestDto = VolatilityBreakoutSellRequestDto()

        val backTesting = volatilityBreakoutStrategy.backTesting(market, start, end, buyRequestDto, sellRequestDto)

        assertThat(backTesting).isNotNull
    }
}