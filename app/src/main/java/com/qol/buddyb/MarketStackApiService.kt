package com.qol.buddyb

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketStackApiService {
    @GET("eod/latest")
    fun getMarketData(
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String
    ): Call<MarketStackResponse> // Use Call<MarketDataResponse> instead of Call<List<MarketData>>
}
