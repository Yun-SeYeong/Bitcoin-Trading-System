package com.demo.bitcointradingsystem.entity

import javax.persistence.*

@Entity
class Rule(
        tradingType: TradingType,
        value1: ValueType,
        value2: ValueType,
        value1Weight: Double,
        value2Weight: Double,
        operator: Operator
) {

    @Id
    @GeneratedValue
    @Column(name = "rule_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    var scenario: Scenario? = null
        protected set

    @Enumerated(EnumType.STRING)
    var tradingType: TradingType = tradingType
        protected set

    @Enumerated(EnumType.STRING)
    var value1: ValueType = value1
        protected set

    @Enumerated(EnumType.STRING)
    var value2: ValueType = value2
        protected set

    var value1Weight: Double = value1Weight
        protected set

    var value2Weight: Double = value2Weight
        protected set

    @Enumerated(EnumType.STRING)
    var operator: Operator = operator
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rule

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Rule(id=$id, value1=$value1, value2=$value2, value1Weight=$value1Weight, value2Weight=$value2Weight, operator=$operator)"
    }
}