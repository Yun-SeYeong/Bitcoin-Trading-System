package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.CandleKey
import com.demo.bitcointradingsystem.entity.DayCandle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DayCandleRepository : JpaRepository<DayCandle, CandleKey>{

    @Query("select m from DayCandle m where m.candleKey.market = :market order by m.candleKey.timestamp desc")
    fun findByMarket(@Param("market") market: String): List<DayCandle>
}