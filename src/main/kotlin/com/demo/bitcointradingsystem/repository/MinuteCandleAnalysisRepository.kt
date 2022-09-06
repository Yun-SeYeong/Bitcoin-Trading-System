package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.MinuteCandleAnalysis
import com.demo.bitcointradingsystem.entity.MinuteCandleKey
import com.demo.bitcointradingsystem.entity.MinuteCandleMacd
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MinuteCandleAnalysisRepository : JpaRepository<MinuteCandleAnalysis, MinuteCandleKey> {

    @Query("select mc from MinuteCandleMacd mc where mc.minuteCandleKey.market = :market and mc.minuteCandleKey.unit = :unit order by mc.minuteCandleKey.timestamp desc")
    fun findByMarketAndUnit(@Param("market") market: String, @Param("unit") unit: Int): List<MinuteCandleMacd>
}