package com.demo.bitcointradingsystem.controller

import com.demo.bitcointradingsystem.dto.responseDto.*
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
    ): ResponseDto<List<GetMinuteCandleV1Dto>> {
        return ResponseDto(candleService.getMinuteCandle(market, unit, count))
    }

    @GetMapping("day-candle/v1")
    fun getMinuteCandleV1(
            @RequestParam market: String,
            @RequestParam count: Int
    ): ResponseDto<List<GetDayCandleV1Dto>> {
        return ResponseDto(candleService.getDayCandle(market, count))
    }

    @GetMapping("market-code/v1")
    fun getMarketCodeV1(): ResponseDto<List<GetMarketCodeV1Dto>> {
        return ResponseDto(candleService.getMarketCodeV1())
    }

    @GetMapping("market-code/v2")
    fun getMarketCodeV2(): ResponseDto<List<GetMarketCodeV2Dto>> {
        return ResponseDto(candleService.getMarketCodeV2())
    }

}