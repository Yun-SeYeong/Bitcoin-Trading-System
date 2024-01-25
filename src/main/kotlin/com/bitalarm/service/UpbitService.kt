package com.bitalarm.service

import com.bitalarm.dto.upbit.*
import com.demo.bitcointradingsystem.util.UpbitUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.util.HashMap


@Service
@Transactional
class UpbitService(
    private val webClient: WebClient
) {

    @Value("\${upbit.access-key}")
    private lateinit var accessKey: String

    @Value("\${upbit.secret-key}")
    private lateinit var secretKey: String

    fun getMarketAll(isDetails: Boolean): List<MarketCodeResponse>? {
        return webClient.get().run {
            uri {
                it.run {
                    path("/market/all")
                    queryParam("isDetails", isDetails)
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            header("accept", "application/json")
            retrieve()
        }.bodyToMono(Array<MarketCodeResponse>::class.java).block()?.toList()
    }

    fun getTicker(market: String): List<TickerResponse>? {
        return webClient.get().run {
            uri {
                it.run {
                    path("/ticker")
                    queryParam("markets", market)
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            retrieve()
        }.bodyToMono(Array<TickerResponse>::class.java).block()?.toList()
    }

    fun getCandlesDays(market: String, count: Int, to: String?): List<DayCandleResponse>? {
        return webClient.get().run {
            uri {
                it.run {
                    path("/candles/days")
                    queryParam("market", market)
                    queryParam("count", count)
                    to?.let { queryParam("to", to) }
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            retrieve()
        }.bodyToMono(Array<DayCandleResponse>::class.java).block()?.toList()
    }

    fun getAccounts(): List<BalanceResponse>? {
        return webClient.get().run {
            uri {
                it.run {
                    path("/accounts")
                    build()
                }
            }
            accept(MediaType.APPLICATION_JSON)
            header("Authorization", UpbitUtil.generateToken(accessKey, secretKey))
            header("Accept", "application/json")
            retrieve()
        }.bodyToMono(Array<BalanceResponse>::class.java).block()?.toList()
    }

    fun postBuyOrder(
        market: String,
        side: String,
        volume: String?,
        price: String?,
        ordType: String
    ): OrderResponse? {
        val body = HashMap<String, String>()
        body.run {
            put("market", market)
            put("side", side)
            volume?.let { put("volume", it) }
            price?.let { put("price", it) }
            put("ord_type", ordType)
        }

        return webClient.post().run {
            uri {
                it.run {
                    path("/orders")
                    build()
                }
            }
            body(BodyInserters.fromValue(body))
            accept(MediaType.APPLICATION_JSON)
            contentType(MediaType.APPLICATION_JSON)
            header(
                "Authorization",
                UpbitUtil.getAuthenticationTokenWithParam(accessKey, secretKey, body)
            )
            retrieve()
        }.bodyToMono(OrderResponse::class.java).block()
    }

    fun postBuyOrder(market: String, price: String): OrderResponse? {
        return postBuyOrder(
            market = market,
            side = "bid",
            price = price,
            volume = null,
            ordType = "price"
        )
    }

    fun postSellOrder(market: String, volume: String): OrderResponse? {
        return postBuyOrder(
            market = market,
            side = "ask",
            price = null,
            volume = volume,
            ordType = "market"
        )
    }
}