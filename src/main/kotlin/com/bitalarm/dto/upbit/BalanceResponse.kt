package com.bitalarm.dto.upbit

data class BalanceResponse(
    val currency: String,
    val balance: String,
    val locked: String,
    val avgBuyPrice: String,
    val avgBuyPriceModified: Boolean,
    val unitCurrency: String
)