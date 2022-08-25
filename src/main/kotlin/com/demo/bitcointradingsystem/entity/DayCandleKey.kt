package com.demo.bitcointradingsystem.entity

import java.io.Serializable

class DayCandleKey(
        private var market: String? = null,
        private var timestamp: Long? = null
) : Serializable