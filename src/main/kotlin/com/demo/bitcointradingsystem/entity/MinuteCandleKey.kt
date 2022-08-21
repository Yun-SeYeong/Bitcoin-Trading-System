package com.demo.bitcointradingsystem.entity

import java.io.Serializable

class MinuteCandleKey (
        private var market: String? = null,
        private var timestamp: Long? = null
): Serializable