package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.analysis.DayCandleAnalysis
import com.demo.bitcointradingsystem.entity.analysis.DayCandleMacd
import com.demo.bitcointradingsystem.entity.candle.DayCandleKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DayCandleAnalysisRepository : JpaRepository<DayCandleAnalysis, DayCandleKey> {
    @Query("select ma from DayCandleMacd ma where ma.dayCandleKey.market = :market order by ma.dayCandleKey.timestamp desc")
    fun findByMarket(@Param("market") market: String): List<DayCandleMacd>
}