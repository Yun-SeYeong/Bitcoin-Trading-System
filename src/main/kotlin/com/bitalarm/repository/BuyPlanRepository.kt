package com.bitalarm.repository

import com.bitalarm.domain.BuyPlan
import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Date
import java.util.Optional

interface BuyPlanRepository: JpaRepository<BuyPlan, Long> {
    fun findByPlanDateAndMarket(planDate: Date, market: String): Optional<BuyPlan>
    fun findByIsBuy(isBuy: Boolean): List<BuyPlan>
}