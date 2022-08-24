package com.demo.bitcointradingsystem.entity

import java.io.Serializable

class DayCandleKey(
        private var market: String,
        private var timestamp: Long
) : Serializable