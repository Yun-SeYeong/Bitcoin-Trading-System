package com.demo.bitcointradingsystem.entity.candle

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class DayCandleKey(
        var market: String,
        var timestamp: Long,
): Serializable