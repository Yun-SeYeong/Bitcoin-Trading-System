package com.demo.bitcointradingsystem.entity

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Inheritance

@Entity
@Inheritance
class MinuteCandleAnalysis(
        minuteCandleKey: MinuteCandleKey
) {
    @EmbeddedId
    var minuteCandleKey: MinuteCandleKey = minuteCandleKey
        protected set

    override fun toString(): String {
        return "MinuteCandleAnalysis(minuteCandleKey=$minuteCandleKey)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MinuteCandleAnalysis

        if (minuteCandleKey != other.minuteCandleKey) return false

        return true
    }

    override fun hashCode(): Int {
        return minuteCandleKey.hashCode()
    }


}