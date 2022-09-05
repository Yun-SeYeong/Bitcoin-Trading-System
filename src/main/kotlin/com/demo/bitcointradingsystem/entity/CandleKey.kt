package com.demo.bitcointradingsystem.entity

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class CandleKey(
        var market: String,
        var timestamp: Long,
): Serializable