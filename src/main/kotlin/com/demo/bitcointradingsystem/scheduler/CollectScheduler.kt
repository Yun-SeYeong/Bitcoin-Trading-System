package com.demo.bitcointradingsystem.scheduler

import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.sync.SyncService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CollectScheduler(
        private val syncService: SyncService,
        private val marketCodeRepository: MarketCodeRepository
) {

    @Scheduled(cron = "0 0 0 * * *")
    fun collectDayCandle() {
        val marketCodeList = marketCodeRepository.findAll()

        marketCodeList.forEach {
            syncService.syncLastDayCandle(it.market)
        }
    }
}