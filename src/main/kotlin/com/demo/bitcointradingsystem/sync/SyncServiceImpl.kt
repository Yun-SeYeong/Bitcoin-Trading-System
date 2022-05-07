package com.demo.bitcointradingsystem.sync

import com.demo.bitcointradingsystem.dto.DayCandle
import com.demo.bitcointradingsystem.dto.MarketCode
import com.demo.bitcointradingsystem.dto.MinuteCandle
import com.demo.bitcointradingsystem.repository.DayCandleRepository
import com.demo.bitcointradingsystem.repository.MarketCodeRepository
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
import com.demo.bitcointradingsystem.upbit.UpbitService
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

    override fun syncMinuteCandle(unit: Int, market: String, count: Int): List<MinuteCandle>? {
        val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count)
        if (minuteCandleArray != null) {
            return minuteCandleRepository.saveAll(minuteCandleArray)
        }
        return null
    }

    override fun syncDayCandle(market: String, count: Int): List<DayCandle>? {
        val candlesDays = upbitService.getCandlesDays(market, count)
        if (candlesDays != null) {
            return dayCandleRepository.saveAll(candlesDays)
        }
        return null
    }

    override fun syncMarketCode(isDetails: Boolean): List<MarketCode>? {
        val candlesDays = upbitService.getMarketAll(isDetails)
        if (candlesDays != null) {
            return marketCodeRepository.saveAll(candlesDays)
        }
        return null
    }
}