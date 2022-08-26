package com.demo.bitcointradingsystem.controller

import com.demo.bitcointradingsystem.dto.responseDto.PostSyncDayCandleV1Dto
import com.demo.bitcointradingsystem.dto.responseDto.PostSyncMarketCodeV1Dto
import com.demo.bitcointradingsystem.dto.responseDto.PostSyncMinuteCandleV1Dto
import com.demo.bitcointradingsystem.dto.responseDto.ResponseDto
import com.demo.bitcointradingsystem.service.sync.SyncService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sync")
class SyncController(
        private val syncService: SyncService
) {

    @PostMapping("/market-code/v1")
    fun syncMarketCodeV1(): ResponseDto<PostSyncMarketCodeV1Dto>{
        return ResponseDto(PostSyncMarketCodeV1Dto(syncService.syncMarketCode(true).size))
    }

    @PostMapping("minute-candle/v1/{unit}")
    fun syncMinuteCandleV1(
            @PathVariable unit: Int,
            @RequestParam market: String,
            @RequestParam to: String,
            @RequestParam count: Int
    ) : ResponseDto<PostSyncMinuteCandleV1Dto> {
        return ResponseDto(PostSyncMinuteCandleV1Dto(syncService.syncMinuteCandleWithDate(unit, market, count, to).size))
    }

    @PostMapping("day-candle/v1")
    fun syncDayCandleV1(
            @RequestParam market: String,
            @RequestParam to: String,
            @RequestParam count: Int
    ) : ResponseDto<PostSyncDayCandleV1Dto> {
        return ResponseDto(PostSyncDayCandleV1Dto(syncService.syncDayCandleWithDate(market, count, to).size))
    }
}