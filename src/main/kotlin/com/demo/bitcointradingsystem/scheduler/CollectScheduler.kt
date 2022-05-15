package com.demo.bitcointradingsystem.scheduler

import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.sync.SyncService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class CollectScheduler(
        private val syncService: SyncService,
        private val marketCodeRepository: MarketCodeRepository
) {

    @Scheduled(cron = "0 0 0 * * *")
    fun collectMarketCode() {
        syncService.syncMarketCode(true)
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun collectDayCandle() {
        val marketCodeList = marketCodeRepository.findAll()

        marketCodeList.forEach {
            syncService.syncLastDayCandle(it.market)
        }
    }

    @Scheduled(cron = "0 0/3 * * * *")
    fun collect3MinuteCandle() {
        println("[SYNC] 3분봉 수집 시작")
        val marketCodeList = marketCodeRepository.findAll()

        marketCodeList.forEach {
            println("[" + it.market + "] sync...")
            syncService.syncMinuteCandle(3, it.market, 2)
            Thread.sleep(200);
            println("[" + it.market + "] sync complete!")
        }

        println("[SYNC] 3분봉 수집 완료")
    }
}