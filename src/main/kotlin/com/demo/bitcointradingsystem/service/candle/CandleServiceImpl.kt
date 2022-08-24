package com.demo.bitcointradingsystem.service.candle

import com.demo.bitcointradingsystem.dto.responseDto.GetMinuteCandleV1Dto
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class CandleServiceImpl(private val em: EntityManager) : CandleService {

    @Override
    override fun getMinuteCandle(market: String, unit: Int, count: Int): List<GetMinuteCandleV1Dto> {
        return em.createQuery(
                "select " +
                        "new com.demo.bitcointradingsystem.dto.responseDto.GetMinuteCandleV1Dto(mc.market, mc.candleDateTimeKst, mc.openingPrice, mc.highPrice, mc.lowPrice, mc.tradePrice, mc.candleAccTradeVolume) " +
                        "from MinuteCandle mc " +
                        "where mc.market=:market and mc.unit=:unit " +
                        "order by mc.candleDateTimeKst desc", GetMinuteCandleV1Dto::class.java)
                .setParameter("market", market)
                .setParameter("unit", unit)
                .setMaxResults(count)
                .resultList
    }

}