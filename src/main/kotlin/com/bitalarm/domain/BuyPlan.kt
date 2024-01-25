package com.bitalarm.domain

import com.blog.domain.base.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.sql.Date

@Entity
class BuyPlan(
    planDate: Date,
    market: String,
    ma5: Double?,
    ma10: Double?,
    ma20: Double?,
    ma60: Double?,
    prevClosingPrice: Double?,
    isBuy: Boolean
): PrimaryKeyEntity() {
    @Column(nullable = false)
    var planDate: Date = planDate
        protected set
    @Column(nullable = false)
    var market: String = market
        protected set
    @Column
    var ma5: Double? = ma5
        protected set
    @Column
    var ma10: Double? = ma10
        protected set
    @Column
    var ma20: Double? = ma20
        protected set
    @Column
    var ma60: Double? = ma60
        protected set
    @Column
    var prevClosingPrice: Double? = prevClosingPrice
        protected set
    @Column(nullable = false)
    var isBuy: Boolean = isBuy
        protected set

    fun update(
        ma5: Double?,
        ma10: Double?,
        ma20: Double?,
        ma60: Double?,
        prevClosingPrice: Double?,
        isBuy: Boolean
    ) {
        this.ma5 = ma5
        this.ma10 = ma10
        this.ma20 = ma20
        this.ma60 = ma60
        this.prevClosingPrice = prevClosingPrice
        this.isBuy = isBuy
    }
}