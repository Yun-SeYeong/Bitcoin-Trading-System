package com.demo.bitcointradingsystem.sync

import com.demo.bitcointradingsystem.dto.MinuteCandle

interface SyncService {
    fun syncMinuteCandle(unit: Int, market: String, count: Int) : List<MinuteCandle>?
}