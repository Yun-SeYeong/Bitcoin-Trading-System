package com.demo.bitcointradingsystem.dto

import java.io.Serializable

class DayCandleKey : Serializable{
    private var market: String? = null
    private var timestamp: Long? = null
}