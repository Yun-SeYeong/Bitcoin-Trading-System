package com.demo.bitcointradingsystem.controller

import com.demo.bitcointradingsystem.dto.responseDto.PostSyncMarketCodeV1Dto
import com.demo.bitcointradingsystem.dto.responseDto.ResponseDto
import com.demo.bitcointradingsystem.service.sync.SyncService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sync")
class SyncController(
        private val syncService: SyncService
) {

    @PostMapping("/market-code/v1")
    fun syncMarketCodeV1(): ResponseDto<PostSyncMarketCodeV1Dto>{
        return ResponseDto(PostSyncMarketCodeV1Dto(syncService.syncMarketCode(true).size))
    }
}