package com.demo.bitcointradingsystem.service.sync

import com.demo.bitcointradingsystem.entity.DayCandle
import com.demo.bitcointradingsystem.entity.MarketCode
import com.demo.bitcointradingsystem.entity.MinuteCandle
import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
import com.demo.bitcointradingsystem.service.upbit.UpbitService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class SyncServiceImpl(
        private val upbitService: UpbitService,
        private val minuteCandleRepository: MinuteCandleRepository,
        private val dayCandleRepository: DayCandleRepository,
        private val marketCodeRepository: MarketCodeRepository
) : SyncService {

    override fun syncMinuteCandle(unit: Int, market: String, count: Int): List<MinuteCandle> {
        val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count, "")
                ?: throw Exception("Fail to load upbit data")

        return minuteCandleRepository.saveAll(minuteCandleArray)
    }

    override fun syncMinuteCandleWithDate(unit: Int, market: String, count: Int, to: String): List<MinuteCandle> {
        val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count, to)
                ?: throw Exception("Fail to load upbit data")

        return minuteCandleRepository.saveAll(minuteCandleArray)
    }

    override fun syncDayCandle(market: String, count: Int): List<DayCandle> {
        val candlesDays = upbitService.getCandlesDays(market, count, "") ?: throw Exception("Fail to load upbit data")

        return dayCandleRepository.saveAll(candlesDays)
    }

    override fun syncDayCandleWithDate(market: String, count: Int, to: String): List<DayCandle> {
        val candlesDays = upbitService.getCandlesDays(market, count, to) ?: throw Exception("Fail to load upbit data")

        return dayCandleRepository.saveAll(candlesDays)
    }

    override fun syncLastDayCandle(market: String): DayCandle {
        val candlesDays = upbitService.getCandlesDays(market, 2, "") ?: throw Exception("Fail to load upbit data")

        return dayCandleRepository.save(candlesDays[1])
    }

    override fun syncMarketCode(isDetails: Boolean): List<MarketCode> {
        val candlesDays = upbitService.getMarketAll(isDetails) ?: throw Exception("Fail to load upbit data")

        return marketCodeRepository.saveAll(candlesDays)
    }
}