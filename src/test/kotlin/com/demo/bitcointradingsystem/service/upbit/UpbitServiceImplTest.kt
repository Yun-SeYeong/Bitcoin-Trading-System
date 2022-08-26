package com.demo.bitcointradingsystem.service.upbit

import com.demo.bitcointradingsystem.util.UpbitUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class UpbitServiceImplTest {

    @Autowired
    private lateinit var upbitService: UpbitService

    @Value("\${upbit.access-key}")
    private lateinit var accessKey: String

    @Value("\${upbit.secret-key}")
    private lateinit var secretKey: String

    @Test
    @DisplayName("Upbit API를 통해 마켓코드 조회")
    fun getMarketAll() {
        // given
        val isDetails = false

        // when
        val marketCodeArray = upbitService.getMarketAll(isDetails)

        // then
        assertThat(marketCodeArray!!.size).isGreaterThan(0)
    }

    @Test
    @DisplayName("Upbit API를 통해 분봉 데이터 조회")
    fun getCandlesMinutes() {
        // given
        val unit = 1
        val count = 10
        val market = "KRW-BTC"

        // when
        val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count, "")

        // then
        assertThat(minuteCandleArray!!.size).isEqualTo(count)
    }

    @Test
    @DisplayName("Upbit API를 통해 일봉 데이터 조회")
    fun getCandlesDays() {
        // given
        val count = 10
        val market = "KRW-BTC"

        // when
        val dayCandleArray = upbitService.getCandlesDays(market, count, "")

        // then
        assertThat(dayCandleArray!!.size).isEqualTo(count)
    }

    @Test
    @DisplayName("Upbit API를 통해 자산정보 조회")
    fun getAccounts() {
        // given
        val authorization = UpbitUtil.generateToken(accessKey, secretKey)

        // when
        val balanceArray = upbitService.getAccounts(authorization)

        // then
        assertThat(balanceArray).isNotNull
    }

}