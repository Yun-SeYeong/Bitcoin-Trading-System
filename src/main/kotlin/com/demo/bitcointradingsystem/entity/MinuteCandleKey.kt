package com.demo.bitcointradingsystem.entity

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class MinuteCandleKey (
        var market: String,
        var timestamp: Long,
        var unit: Int
): Serializable