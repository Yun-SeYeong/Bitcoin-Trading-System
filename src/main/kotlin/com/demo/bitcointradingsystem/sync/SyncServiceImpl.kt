package com.demo.bitcointradingsystem.sync

import com.demo.bitcointradingsystem.dto.MinuteCandle
import com.demo.bitcointradingsystem.repository.MinuteCandleRepository
import com.demo.bitcointradingsystem.upbit.UpbitService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class SyncServiceImpl(
        private val upbitService: UpbitService,
        private val minuteCandleRepository: MinuteCandleRepository
) : SyncService {

    override fun syncMinuteCandle(unit: Int, market: String, count: Int): List<MinuteCandle>? {
        val minuteCandleArray = upbitService.getCandlesMinutes(unit, market, count)
        if (minuteCandleArray != null) {
            return minuteCandleRepository.saveAll(minuteCandleArray)
        }
        return null
    }
}