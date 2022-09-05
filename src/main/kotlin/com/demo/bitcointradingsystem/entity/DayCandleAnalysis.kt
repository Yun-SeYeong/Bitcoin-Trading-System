package com.demo.bitcointradingsystem.entity

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Inheritance

@Entity
@Inheritance
class DayCandleAnalysis(
        candleKey: CandleKey
) {
    @EmbeddedId
    var candleKey: CandleKey = candleKey
        protected set

    override fun toString(): String {
        return "DayCandleAnalysis(candleKey=$candleKey)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DayCandleAnalysis

        if (candleKey != other.candleKey) return false

        return true
    }

    override fun hashCode(): Int {
        return candleKey.hashCode()
    }


}