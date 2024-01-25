package com.bitalarm.service

import com.bitalarm.repository.BuyPlanRepository
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SpringBootTest
class UpbitServiceTest {

    @Autowired
    private lateinit var upbitService: UpbitService

    @Autowired
    private lateinit var buyPlanRepository: BuyPlanRepository

    @Test
    fun getAccounts() {
        buyPlanRepository.findByIsBuy(true).map {
            println("it = ${it.market}")
        }
    }

    @Test
    fun getCandle() {
        val candlesDays = upbitService.getCandlesDays(
            "KRW-BTC",
            100,
            LocalDateTime.now(ZoneOffset.UTC)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        )
        println("candlesDays = ${candlesDays}")
    }
}