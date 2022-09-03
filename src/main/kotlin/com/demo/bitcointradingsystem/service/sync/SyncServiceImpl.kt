package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.MarketCode
import com.demo.bitcointradingsystem.entity.MinuteCandle
import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
import com.demo.bitcointradingsystem.service.joblog.LogService
import com.demo.bitcointradingsystem.service.upbit.UpbitService
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Service
class SyncServiceImpl(
        private val upbitService: UpbitService,
        private val minuteCandleRepository: MinuteCandleRepository,
        private val dayCandleRepository: DayCandleRepository,
        private val marketCodeRepository: MarketCodeRepository,
        private val logService: LogService,
        private val jdbcTemplate: JdbcTemplate
) : SyncService {

    @Transactional
    fun insertMinuteCandle(batchSize: Int, subItems: List<MinuteCandle>): Int {
        return jdbcTemplate.batchUpdate("insert into minute_candle " +
                "(candle_acc_trade_price, candle_acc_trade_volume, candle_date_time_kst, candle_date_time_utc, high_price, low_price, opening_price, trade_price, unit, market, timestamp) " +
                "values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", subItems, batchSize) { ps, argument ->
            ps.run {
                setDouble(1, argument.candleAccTradePrice)
                setDouble(2, argument.candleAccTradeVolume)
                setTimestamp(3, Timestamp.valueOf(argument.candleDateTimeKst))
                setTimestamp(4, Timestamp.valueOf(argument.candleDateTimeUtc))
                setDouble(5, argument.highPrice)
                setDouble(6, argument.lowPrice)
                setDouble(7, argument.openingPrice)
                setDouble(8, argument.tradePrice)
                setInt(9, argument.unit)
                setString(10, argument.market)
                setLong(11, argument.timestamp)
            }
        }.sumOf { it.size }
    }

    @Transactional
    fun insertDayCandle(batchSize: Int, subItems: List<DayCandle>): Int {
        return jdbcTemplate.batchUpdate("insert " +
                "into " +
                "day_candle " +
                "(candle_acc_trade_price, candle_acc_trade_volume, candle_date_time_kst, candle_date_time_utc, change_price, change_rate, converted_trade_price, high_price, low_price, opening_price, prev_closing_price, trade_price, market, timestamp) " +
                "values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", subItems, batchSize) { ps, argument ->
            ps.run {
                setDouble(1, argument.candleAccTradePrice)
                setDouble(2, argument.candleAccTradeVolume)
                setTimestamp(3, Timestamp.valueOf(argument.candleDateTimeKst))
                setTimestamp(4, Timestamp.valueOf(argument.candleDateTimeUtc))
                setDouble(5, argument.changePrice)
                setDouble(6, argument.changeRate)
                setDouble(7, argument.convertedTradePrice)
                setDouble(8, argument.highPrice)
                setDouble(9, argument.lowPrice)
                setDouble(10, argument.openingPrice)
                setDouble(11, argument.prevClosingPrice)
                setDouble(12, argument.tradePrice)
                setString(13, argument.market)
                setLong(14, argument.timestamp)
            }
        }.sumOf { it.size }
    }

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

    override fun batchMinuteCandleWithDate(unit: Int, market: String, count: Int, to: String): Int {
        val createLog = logService.createLog("[SYNC SERVICE] start minute candle sync (unit = [${unit}], market = [${market}], count = [${count}], to = [${to}])")
        val resultSize: Int
        try {
            val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count, to)
                    ?: throw Exception("Fail to load upbit data")
            minuteCandleRepository.deleteAllInBatch(minuteCandleArray)
            resultSize = insertMinuteCandle(1000, minuteCandleArray)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] minute candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] minute candle sync success")
        return resultSize
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

    override fun batchDayCandleWithDate(market: String, count: Int, to: String): Int {
        val createLog = logService.createLog("[SYNC SERVICE] start day candle sync (market = [${market}], count = [${count}], to = [${to}])")
        val resultSize: Int
        try {
            val candlesDays = upbitService.getCandlesDays(market, count, to)
                    ?: throw Exception("Fail to load upbit data")
            dayCandleRepository.deleteAllInBatch(candlesDays)
            resultSize = insertDayCandle(1000, candlesDays)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] day candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] day candle sync success")
        return resultSize
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
            marketCodeRepository.deleteAllInBatch()
            saveAll = marketCodeRepository.saveAll(candlesDays)
        } catch (e: Exception) {
            logService.failLog(createLog.id!!, "[SYNC SERVICE] day candle sync fail ($e)")
            throw e
        }
        logService.successLog(createLog.id!!, "[SYNC SERVICE] day candle sync success")
        return saveAll
    }
}