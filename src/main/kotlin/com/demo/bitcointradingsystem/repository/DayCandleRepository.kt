package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.dto.DayCandle
import com.demo.bitcointradingsystem.dto.DayCandleKey
import org.springframework.data.jpa.repository.JpaRepository

interface DayCandleRepository : JpaRepository<DayCandle, DayCandleKey>{
}