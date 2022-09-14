package com.demo.bitcointradingsystem.service.upbit

import com.demo.bitcointradingsystem.dto.*
import com.demo.bitcointradingsystem.entity.*
import com.demo.bitcointradingsystem.util.UpbitUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class UpbitServiceImpl(
        private val webClient: WebClient
) : UpbitService {

    @Value("\${upbit.access-key}")
    private lateinit var accessKey: String

    @Value("\${upbit.secret-key}")
    private lateinit var secretKey: String

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

    override fun getCandlesMinutes(unit: Int, market: String, count: Int, to: String): List<MinuteCandle>? {
        var c = count
        var t = to

        val result = ArrayList<MinuteCandle>()

        while (c > 0) {
            val queryCount = if (c >= 200) 200 else c
            webClient.get().run {
                uri {
                    it.run {
                        path("/candles/minutes/$unit")
                        queryParam("market", market)
                        queryParam("count", queryCount)
                        if (t.isNotEmpty()) queryParam("to", t)
                        build()
                    }
                }
                accept(MediaType.APPLICATION_JSON)
                header("Accept", "application/json")
                retrieve()
            }.bodyToMono(Array<MinuteCandle>::class.java).block()?.map {
                result.add(it)
            }

            c -= queryCount
            t = result.last().candleDateTimeUtc
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        }

        return result
    }

    override fun getCandlesDays(market: String, count: Int, to: String): List<DayCandle>? {
        var c = count
        var t = to

        val result = ArrayList<DayCandle>()

        while (c > 0) {
            val queryCount = if (c >= 200) 200 else c
            webClient.get().run {
                uri {
                    it.run {
                        path("/candles/days")
                        queryParam("market", market)
                        queryParam("count", queryCount)
                        if (t.isNotEmpty()) queryParam("to", t)
                        build()
                    }
                }
                accept(MediaType.APPLICATION_JSON)
                header("Accept", "application/json")
                retrieve()
            }.bodyToMono(Array<DayCandle>::class.java).block()?.map {
                result.add(it)
            }

            c -= queryCount
            t = result.last().candleDateTimeUtc.toString()
        }

        return result
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

    override fun postOrders(authorization: String, market: String, side: String, volume: String, price: String, ordType: String): Order? {
        val body = HashMap<String, String>()
        body.run {
            put("market", market)
            put("side", side)
            put("volume", volume)
            put("price", price)
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
            header("Authorization", UpbitUtil.getAuthenticationTokenWithParam(accessKey, secretKey, body))
            retrieve()
        }.bodyToMono(Order::class.java).block()
    }
}