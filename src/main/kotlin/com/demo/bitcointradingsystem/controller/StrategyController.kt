package com.demo.bitcointradingsystem.controller

import com.demo.bitcointradingsystem.dto.responseDto.ResponseDto
import com.demo.bitcointradingsystem.dto.strategyDto.VolatilityBreakoutBuyRequestDto
import com.demo.bitcointradingsystem.dto.strategyDto.VolatilityBreakoutSellRequestDto
import com.demo.bitcointradingsystem.entity.trading.TradingHistory
import com.demo.bitcointradingsystem.service.strategy.Strategy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("strategy")
class StrategyController(
    private val strategy: Strategy<VolatilityBreakoutBuyRequestDto, VolatilityBreakoutSellRequestDto>
) {

    @GetMapping("/back-testing/volatility-breakout")
    fun getVolatilityBackTesting(
        @RequestParam("market") market: String,
        @RequestParam("start") start: String,
        @RequestParam("end") end: String,
        @RequestParam("k") k: Double
    ) : ResponseDto<List<TradingHistory>> {
        return ResponseDto(strategy.backTesting(market, LocalDate.parse(start), LocalDate.parse(end), VolatilityBreakoutBuyRequestDto(k), VolatilityBreakoutSellRequestDto()))
    }

}