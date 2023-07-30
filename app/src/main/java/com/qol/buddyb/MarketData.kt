package com.qol.buddyb

data class MarketData(
    val symbol: String,
    val exchange: String,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Double
)

