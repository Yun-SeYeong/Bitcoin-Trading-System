package com.bitalarm.dto.request

import com.bitalarm.domain.BuyPlan
import java.sql.Date

data class BuyPlanRequest(
    var planDate: Date,
    var market: String,
    var ma5: Double?,
    var ma10: Double?,
    var ma20: Double?,
    var ma60: Double?,
    var prevClosingPrice: Double?,
    var isBuy: Boolean
) {
    fun toBuyPlan() = BuyPlan(
        planDate = planDate,
        market = market,
        ma5 = ma5,
        ma10 = ma10,
        ma20 = ma20,
        ma60 = ma60,
        prevClosingPrice = prevClosingPrice,
        isBuy = isBuy
    )
}