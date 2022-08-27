package com.demo.bitcointradingsystem.controller

import com.demo.bitcointradingsystem.dto.responseDto.*
import com.demo.bitcointradingsystem.service.joblog.LogService
import com.demo.bitcointradingsystem.service.sync.SyncService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sync")
class SyncController(
        private val syncService: SyncService,
        private val logService: LogService
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

    @GetMapping("log/v1")
    fun getSyncLogV1(
            @PageableDefault(sort = ["startDateTime,desc"]) pageable: Pageable
    ) : ResponseDto<List<GetSyncLogV1Dto>>{
        return ResponseDto(
                logService.getLog(pageable).map {
                    GetSyncLogV1Dto(it.jobId, it.startDateTime, it.endDateTime, it.status.toString(), it.msg)
                }.content
        )
    }
}