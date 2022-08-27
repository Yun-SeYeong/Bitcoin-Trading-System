package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.MarketCode
import com.demo.bitcointradingsystem.entity.MinuteCandle
import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
import com.demo.bitcointradingsystem.service.joblog.LogService
import com.demo.bitcointradingsystem.service.upbit.UpbitService
import org.springframework.stereotype.Service

@Service
class SyncServiceImpl(
        private val upbitService: UpbitService,
        private val minuteCandleRepository: MinuteCandleRepository,
        private val dayCandleRepository: DayCandleRepository,
        private val marketCodeRepository: MarketCodeRepository,
        private val logService: LogService
) : SyncService {

    override fun syncMinuteCandle(unit: Int, market: String, count: Int): List<MinuteCandle> {
        val createLog = logService.createLog("[SYNC SERVICE] start minute candle sync (unit = [${unit}], market = [${market}], count = [${count}])")
        val saveAll: List<MinuteCandle>
        try {
            val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count, "")
                    ?: throw Exception("Fail to load upbit data")
            saveAll = minuteCandleRepository.saveAll(minuteCandleArray)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] minute candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] minute candle sync success")
        return saveAll
    }

    override fun syncMinuteCandleWithDate(unit: Int, market: String, count: Int, to: String): List<MinuteCandle> {
        val createLog = logService.createLog("[SYNC SERVICE] start minute candle sync (unit = [${unit}], market = [${market}], count = [${count}], to = [${to}])")
        val saveAll: List<MinuteCandle>
        try {
            val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count, to)
                    ?: throw Exception("Fail to load upbit data")

            saveAll = minuteCandleRepository.saveAll(minuteCandleArray)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] minute candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] minute candle sync success")
        return saveAll
    }

    override fun syncDayCandle(market: String, count: Int): List<DayCandle> {
        val createLog = logService.createLog("[SYNC SERVICE] start day candle sync (market = [${market}], count = [${count}])")
        val saveAll: List<DayCandle>
        try {
            val candlesDays = upbitService.getCandlesDays(market, count, "")
                    ?: throw Exception("Fail to load upbit data")
            saveAll = dayCandleRepository.saveAll(candlesDays)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] day candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] day candle sync success")
        return saveAll
    }

    override fun syncDayCandleWithDate(market: String, count: Int, to: String): List<DayCandle> {
        val createLog = logService.createLog("[SYNC SERVICE] start day candle sync (market = [${market}], count = [${count}], to = [${to}])")
        val saveAll: List<DayCandle>
        try {
            val candlesDays = upbitService.getCandlesDays(market, count, to)
                    ?: throw Exception("Fail to load upbit data")
            saveAll = dayCandleRepository.saveAll(candlesDays)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] day candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] day candle sync success")
        return saveAll
    }

    override fun syncLastDayCandle(market: String): DayCandle {
        val createLog = logService.createLog("[SYNC SERVICE] start last day candle sync (market = [${market}])")
        val result: DayCandle
        try {
            val candlesDays = upbitService.getCandlesDays(market, 2, "") ?: throw Exception("Fail to load upbit data")
            result = dayCandleRepository.save(candlesDays[1])
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] day candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] day candle sync success")
        return result
    }

    override fun syncMarketCode(isDetails: Boolean): List<MarketCode> {
        val createLog = logService.createLog("[SYNC SERVICE] start market code sync (isDetails = [${isDetails}])")
        val saveAll: List<MarketCode>
        try {
            val candlesDays = upbitService.getMarketAll(isDetails) ?: throw Exception("Fail to load upbit data")
            saveAll = marketCodeRepository.saveAll(candlesDays)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] day candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] day candle sync success")
        return saveAll
    }
}