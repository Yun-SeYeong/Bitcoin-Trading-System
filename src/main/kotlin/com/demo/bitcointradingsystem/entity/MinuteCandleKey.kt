package com.demo.bitcointradingsystem.entity

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class MinuteCandleKey (
        var market: String? = null,
        var timestamp: Long? = null,
        var unit: Int? = null
): Serializable