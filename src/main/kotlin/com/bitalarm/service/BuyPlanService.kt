package com.bitalarm.service

import com.bitalarm.domain.BuyPlan
import com.bitalarm.domain.MarketCode
import com.bitalarm.dto.upbit.DayCandleResponse
import com.bitalarm.dto.request.BuyPlanRequest
import com.bitalarm.dto.response.BuyPlanResponse
import com.bitalarm.repository.BuyPlanRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
@Transactional
class BuyPlanService(
    private val buyPlanRepository: BuyPlanRepository,
    private val upbitService: UpbitService,
) {
    fun getBuyPlan(): List<BuyPlan> {
        return buyPlanRepository.findByIsBuy(true).toList()
    }

    fun createBuyPlan(buyPlanRequest: BuyPlanRequest) {
        val buyPlan = buyPlanRepository.findByPlanDateAndMarket(
            buyPlanRequest.planDate,
            buyPlanRequest.market
        )

        buyPlanRepository.save(
            if (buyPlan.isEmpty) {
                buyPlanRequest.toBuyPlan()
            } else {
                buyPlan.get().apply {
                    update(
                        ma5 = buyPlanRequest.ma5,
                        ma10 = buyPlanRequest.ma10,
                        ma20 = buyPlanRequest.ma20,
                        ma60 = buyPlanRequest.ma60,
                        prevClosingPrice = buyPlanRequest.prevClosingPrice,
                        isBuy = buyPlanRequest.isBuy
                    )
                }
            }
        )

    }

    fun analysis(marketCode: MarketCode): BuyPlanRequest? {
        val now = getNow()
        val planDate = Date(Timestamp.valueOf(now).time)
        return upbitService.getCandlesDays(
            market = marketCode.market,
            count = 200,
            to = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        )?.map(DayCandleResponse::tradePrice)?.let { tradePrices ->
            BuyPlanRequest(
                planDate = planDate,
                market = marketCode.market,
                ma5 = tradePrices.take(5).average(),
                ma10 = tradePrices.take(10).average(),
                ma20 = tradePrices.take(20).average(),
                ma60 = tradePrices.take(60).average(),
                prevClosingPrice = tradePrices.take(1).lastOrNull(),
                isBuy = false
            ).apply {
                if (prevClosingPrice != null && ma5 != null && ma10 != null && ma20 != null && ma60 != null) {
                    isBuy = (prevClosingPrice!! > ma5!! && ma5!! > ma10!! && ma10!! > ma20!! && ma20!! > ma60!!)
                }
            }
        }
    }

    private fun getNow(): LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)

}