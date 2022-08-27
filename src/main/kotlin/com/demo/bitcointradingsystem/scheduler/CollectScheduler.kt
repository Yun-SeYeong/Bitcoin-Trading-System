package com.demo.bitcointradingsystem.scheduler

import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.service.joblog.LogService
import com.demo.bitcointradingsystem.service.sync.SyncService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class CollectScheduler(
        private val syncService: SyncService,
        private val marketCodeRepository: MarketCodeRepository,
        private val logService: LogService
) {

    @Scheduled(cron = "0 0 0 * * *")
    fun collectMarketCode() {
        val createLog = logService.createLog("[SCHEDULER] start minute candle sync")
        try {
            syncService.syncMarketCode(true)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SCHEDULER] minute candle sync fail")
            throw e
        }
        logService.successLog(createLog.id!!, "[SCHEDULER] minute candle sync success")
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun collectDayCandle() {
        val createLog = logService.createLog("[SCHEDULER] start day candle sync")
        try {
            val marketCodeList = marketCodeRepository.findAll()
            marketCodeList.forEach {
                syncService.syncLastDayCandle(it.market)
            }
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SCHEDULER] day candle sync fail")
            throw e
        }
        logService.successLog(createLog.id!!, "[SCHEDULER] day candle sync success")
    }

    @Scheduled(cron = "0 0/3 * * * *")
    fun collect3MinuteCandle() {
        val createLog = logService.createLog("[SCHEDULER] start 3minute candle sync")
        try {
            println("[SYNC] 3분봉 수집 시작")
            val marketCodeList = marketCodeRepository.findAll()

            marketCodeList.forEach {
                println("[" + it.market + "] sync...")
                syncService.syncMinuteCandle(3, it.market, 2)
                Thread.sleep(200);
                println("[" + it.market + "] sync complete!")
            }

            println("[SYNC] 3분봉 수집 완료")
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SCHEDULER] 3minute candle sync fail")
            throw e
        }
        logService.successLog(createLog.id!!, "[SCHEDULER] 3minute candle sync success")
    }
}