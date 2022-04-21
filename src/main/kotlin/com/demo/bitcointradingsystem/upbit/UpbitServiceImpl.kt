package com.demo.bitcointradingsystem.upbit

import com.demo.bitcointradingsystem.dto.Balance
import com.demo.bitcointradingsystem.dto.DayCandle
import com.demo.bitcointradingsystem.dto.MarketCode
import com.demo.bitcointradingsystem.dto.MinuteCandle
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
@RequiredArgsConstructor
class UpbitServiceImpl(
        private val webClient: WebClient
) : UpbitService {

    override fun getMarketAll(isDetails: Boolean): List<MarketCode>? {
        return webClient.get().run {
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
        }.bodyToMono(Array<MarketCode>::class.java).block()?.toList()
    }

    override fun getCandlesMinutes(unit: Int, market: String, count: Int): List<MinuteCandle>? {
        return webClient.get().run {
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
        }.bodyToMono(Array<MinuteCandle>::class.java).block()?.toList()
    }

    override fun getCandlesDays(market: String, count: Int): List<DayCandle>? {
        return webClient.get().run {
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
        }.bodyToMono(Array<DayCandle>::class.java).block()?.toList()
    }

    override fun getAccounts(authorization: String): List<Balance>? {
        return webClient.get().run {
            uri {
                it.run {
                    path("/accounts")
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            header("Authorization", authorization)
            header("Accept", "application/json")
            retrieve()
        }.bodyToMono(Array<Balance>::class.java).block()?.toList()
    }
}