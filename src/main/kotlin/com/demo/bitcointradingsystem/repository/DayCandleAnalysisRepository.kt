package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.CandleKey
import com.demo.bitcointradingsystem.entity.DayCandleAnalysis
import com.demo.bitcointradingsystem.entity.DayCandleMacd
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DayCandleAnalysisRepository : JpaRepository<DayCandleAnalysis, CandleKey>{
    @Query("select ma from DayCandleMacd ma where ma.candleKey.market = :market order by ma.candleKey.timestamp desc")
    fun findByMarket(@Param("market") market: String): List<DayCandleMacd>
}