package com.demo.bitcointradingsystem.dto

import java.io.Serializable


class MinuteCandleKey: Serializable {
    private var market: String? = null
    private var timestamp: Long? = null
}