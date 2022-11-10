package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.candle.DayCandle
import com.demo.bitcointradingsystem.entity.candle.DayCandleKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DayCandleRepository : JpaRepository<DayCandle, DayCandleKey>{
    @Query("select m from DayCandle m where m.dayCandleKey.market = :market order by m.dayCandleKey.timestamp desc")
    fun findByMarket(@Param("market") market: String): List<DayCandle>
}