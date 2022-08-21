package com.demo.bitcointradingsystem.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class MarketCode(
        @Id
        val market: String,
        val koreanName: String,
        val englishName: String,
        val marketWarning: String?
)
