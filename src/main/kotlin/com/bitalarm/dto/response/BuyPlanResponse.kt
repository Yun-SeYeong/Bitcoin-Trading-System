package com.bitalarm.dto.response

import com.bitalarm.domain.BuyPlan
import java.util.*

class BuyPlanResponse(
    val id: Long?,
    val planDate: Date,
    val market: String,
    val ma5: Double?,
    val ma10: Double?,
    val ma20: Double?,
    val ma60: Double?,
    val prevClosingPrice: Double?
) {
    companion object {
        fun of(buyPlan: BuyPlan): BuyPlanResponse {
            return BuyPlanResponse(
                id = buyPlan.id,
                planDate = buyPlan.planDate,
                market = buyPlan.market,
                ma5 = buyPlan.ma5,
                ma10 = buyPlan.ma10,
                ma20 = buyPlan.ma20,
                ma60 = buyPlan.ma60,
                prevClosingPrice = buyPlan.prevClosingPrice
            )
        }
    }
}