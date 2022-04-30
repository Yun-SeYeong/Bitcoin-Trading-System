package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.dto.MinuteCandle
import com.demo.bitcointradingsystem.dto.MinuteCandleKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MinuteCandleRepository : JpaRepository<MinuteCandle,MinuteCandleKey >{
}