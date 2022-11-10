package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.dto.responseDto.GetMarketCodeV2Dto
import com.demo.bitcointradingsystem.entity.candle.MarketCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MarketCodeRepository : JpaRepository<MarketCode, String> {

    @Query("select new com.demo.bitcointradingsystem.dto.responseDto.GetMarketCodeV2Dto(mc.market, mc.koreanName, mc.englishName, mc.createdDate) from MarketCode mc order by mc.createdDate")
    fun getMarketCodeV2Dto() : List<GetMarketCodeV2Dto>

}