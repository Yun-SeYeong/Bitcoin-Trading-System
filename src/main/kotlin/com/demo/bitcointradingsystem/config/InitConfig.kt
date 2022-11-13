package com.demo.bitcointradingsystem.config

import com.demo.bitcointradingsystem.service.sync.SyncService
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class InitConfig(
    private val syncService: SyncService
) {

    @PostConstruct
    fun init() {
        syncService.syncMarketCode(false)
        syncService.syncMinuteCandle(3, "KRW-BTC", 100)
        syncService.syncDayCandle("KRW-BTC", 100)
    }
}