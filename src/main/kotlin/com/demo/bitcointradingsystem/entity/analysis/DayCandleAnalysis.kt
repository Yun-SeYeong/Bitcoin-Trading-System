package com.demo.bitcointradingsystem.entity.analysis

import com.demo.bitcointradingsystem.entity.candle.DayCandleKey
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Inheritance

@Entity
@Inheritance
class DayCandleAnalysis(
        dayCandleKey: DayCandleKey
) {
    @EmbeddedId
    var dayCandleKey: DayCandleKey = dayCandleKey
        protected set

    override fun toString(): String {
        return "DayCandleAnalysis(candleKey=$dayCandleKey)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DayCandleAnalysis

        if (dayCandleKey != other.dayCandleKey) return false

        return true
    }

    override fun hashCode(): Int {
        return dayCandleKey.hashCode()
    }


}