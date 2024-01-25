package com.bitalarm.dto.upbit

data class OrderResponse(
        val uuid: String,
        val side: String?,
        val ordType: String?,
        val price: String?,
        val avgPrice: String?,
        val state: String?,
        val market: String?,
        val createdAt: String?,
        val volume: String?,
        val remainingVolume: String?,
        val reservedFee: String?,
        val remainingFee: String?,
        val paidFee: String?,
        val locked: String?,
        val executedVolume: String?,
        val tradeCount: Int?
)