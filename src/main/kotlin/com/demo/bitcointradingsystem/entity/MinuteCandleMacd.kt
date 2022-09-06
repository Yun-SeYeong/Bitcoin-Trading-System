package com.demo.bitcointradingsystem.entity

import javax.persistence.Entity

@Entity
class MinuteCandleMacd(
        minuteCandleKey: MinuteCandleKey,
        ma5: Double?,
        ma10: Double?,
        ma15: Double?,
        ma20: Double?,
        ma60: Double?,
        ma120: Double?,
) : MinuteCandleAnalysis(minuteCandleKey) {
    var ma5: Double? = ma5
        protected set
    var ma10: Double? = ma10
        protected set
    var ma15: Double? = ma15
        protected set
    var ma20: Double? = ma20
        protected set
    var ma60: Double? = ma60
        protected set
    var ma120: Double? = ma120
        protected set

    override fun toString(): String {
        return "MinuteCandleMacd(candleKey=${super.minuteCandleKey}, ma5=$ma5, ma10=$ma10, ma15=$ma15, ma20=$ma20, ma60=$ma60, ma120=$ma120)"
    }


}