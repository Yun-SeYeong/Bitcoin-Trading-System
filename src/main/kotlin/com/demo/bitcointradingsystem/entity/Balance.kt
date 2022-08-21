package com.demo.bitcointradingsystem.entity

data class Balance(
        val currency: String,
        val balance: String,
        val locked: String,
        val avgBuyPrice: String,
        val avgBuyPriceModified: Boolean,
        val unitCurrency: String
)
