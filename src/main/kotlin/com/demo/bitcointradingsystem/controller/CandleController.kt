package com.demo.bitcointradingsystem.controller

import com.demo.bitcointradingsystem.dto.responseDto.GetMinuteCandleV1Dto
import com.demo.bitcointradingsystem.dto.responseDto.GetResponseDto
import com.demo.bitcointradingsystem.service.candle.CandleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/candles")
class CandleController(
        private val candleService: CandleService
) {

    @GetMapping("minute-candle/v1/{unit}")
    fun getMinuteCandleV1(
            @PathVariable unit: Int,
            @RequestParam market: String,
            @RequestParam count: Int
    ): GetResponseDto<List<GetMinuteCandleV1Dto>> {
        return GetResponseDto(candleService.getMinuteCandle(market, unit, count))
    }

}