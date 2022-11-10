package com.demo.bitcointradingsystem.service.candle

import com.demo.bitcointradingsystem.dto.responseDto.*
import com.demo.bitcointradingsystem.entity.analysis.DayCandleMacd
import com.demo.bitcointradingsystem.entity.analysis.MinuteCandleMacd
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityManager

@Service
class CandleServiceImpl(
        private val em: EntityManager,
        private val marketCodeRepository: MarketCodeRepository
) : CandleService {

    @Override
    override fun getMinuteCandle(market: String, unit: Int, count: Int): List<GetMinuteCandleV1Dto> {
        return em.createQuery(
                "select " +
                        "new com.demo.bitcointradingsystem.dto.responseDto.GetMinuteCandleV1Dto(mc.minuteCandleKey.market, mc.candleDateTimeKst, mc.openingPrice, mc.highPrice, mc.lowPrice, mc.tradePrice, mc.candleAccTradeVolume) " +
                        "from MinuteCandle mc " +
                        "where mc.minuteCandleKey.market=:market and mc.minuteCandleKey.unit=:unit " +
                        "order by mc.candleDateTimeKst desc", GetMinuteCandleV1Dto::class.java)
                .setParameter("market", market)
                .setParameter("unit", unit)
                .setMaxResults(count)
                .resultList
    }

    @Override
    override fun getDayCandle(market: String, count: Int): List<GetDayCandleV1Dto> {
        return em.createQuery(
                "select new com.demo.bitcointradingsystem.dto.responseDto.GetDayCandleV1Dto(dc.dayCandleKey.market, dc.candleDateTimeKst, dc.openingPrice, dc.highPrice, dc.lowPrice, dc.tradePrice, dc.candleAccTradeVolume) " +
                        "from DayCandle dc " +
                        "where dc.dayCandleKey.market=:market " +
                        "order by dc.candleDateTimeKst desc", GetDayCandleV1Dto::class.java)
                .setParameter("market", market)
                .setMaxResults(count)
                .resultList
    }

    override fun getMarketCodeV1(): List<GetMarketCodeV1Dto> {
        return em.createQuery(
                "select new com.demo.bitcointradingsystem.dto.responseDto.GetMarketCodeV1Dto(mc.market, mc.koreanName, mc.englishName) " +
                        "from MarketCode mc", GetMarketCodeV1Dto::class.java)
                .resultList
    }

    override fun getMarketCodeV2(): List<GetMarketCodeV2Dto> {
        return marketCodeRepository.getMarketCodeV2Dto()
    }

    override fun getDayCandleMa(market: String, count: Int): List<GetDayCandleMaV1Dto> {
        return em.createQuery("select ma from DayCandleMacd ma where ma.dayCandleKey.market = :market order by ma.dayCandleKey.timestamp desc", DayCandleMacd::class.java)
                .setParameter("market", market)
                .setMaxResults(count)
                .resultList
                .map {
                    GetDayCandleMaV1Dto(
                            it.dayCandleKey.market,
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(it.dayCandleKey.timestamp), TimeZone.getDefault().toZoneId()).toString(),
                            it.ma5, it.ma10, it.ma15, it.ma20, it.ma60, it.ma120)
                }
    }

    override fun getMinuteCandleMa(market: String, count: Int): List<GetMinuteCandleMaV1Dto> {
        return em.createQuery("select ma from MinuteCandleMacd ma where ma.minuteCandleKey.market = :market order by ma.minuteCandleKey.timestamp desc", MinuteCandleMacd::class.java)
                .setParameter("market", market)
                .setMaxResults(count)
                .resultList
                .map {
                    GetMinuteCandleMaV1Dto(
                            it.minuteCandleKey.market!!,
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(it.minuteCandleKey.timestamp!!), TimeZone.getDefault().toZoneId()).toString(),
                            it.ma5, it.ma10, it.ma15, it.ma20, it.ma60, it.ma120)
                }
    }
}