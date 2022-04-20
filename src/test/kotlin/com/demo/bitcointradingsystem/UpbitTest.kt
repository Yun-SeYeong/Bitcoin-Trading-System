package com.demo.bitcointradingsystem

import com.demo.bitcointradingsystem.dto.DayCandle
import com.demo.bitcointradingsystem.dto.MarketCode
import com.demo.bitcointradingsystem.dto.MinuteCandle
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest
class UpbitTest {

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    @DisplayName("Upbit API를 통해 마켓코드 조회")
    fun getMarketAll() {
        // given
        val isDetails = false

        // when
        val marketCodeArray = webClient.get().run {
            uri {
                it.run {
                    path("/market/all")
                    queryParam("isDetails", isDetails)
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            header("Accept", "application/json")
            retrieve()
        }.bodyToMono(Array<MarketCode>::class.java).block()

        marketCodeArray!!.forEach {
            println("it = ${it}")
        }

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
        val minuteCandleArray = webClient.get().run {
            uri {
                it.run {
                    path("/candles/minutes/$unit")
                    queryParam("market", market)
                    queryParam("count", count)
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            header("Accept", "application/json")
            retrieve()
        }.bodyToMono(Array<MinuteCandle>::class.java).block()

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
        val minuteCandleArray = webClient.get().run {
            uri {
                it.run {
                    path("/candles/days")
                    queryParam("market", market)
                    queryParam("count", count)
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            header("Accept", "application/json")
            retrieve()
        }.bodyToMono(Array<DayCandle>::class.java).block()

        // then
        assertEquals(minuteCandleArray!!.size, count)
    }
}