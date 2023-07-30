package com.qol.buddyb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Investing : Fragment() {
    // ...

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_investing, container, false)

        fetchappledata(rootView)
        fetchgoogledata(rootView)

        return rootView
    }

    private fun fetchgoogledata(rootView: View) {
        val baseUrl = "http://api.marketstack.com/v1/"
        val accessKey = "4794206a750f0b853e937da6726c67c3"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val marketStackService = retrofit.create(MarketStackApiService::class.java)
        val symbols = "GOOG"

        val call: Call<MarketStackResponse> = marketStackService.getMarketData(accessKey, symbols)
        call.enqueue(object : Callback<MarketStackResponse> {
            override fun onResponse(call: Call<MarketStackResponse>, response: Response<MarketStackResponse>) {
                if (response.isSuccessful) {
                    val marketDataResponse = response.body()
                    if (marketDataResponse != null) {
                        val marketDataList = marketDataResponse.data
                        if (marketDataList.isNotEmpty()) {
                            // Use the marketDataList[0] (first item) here to access the symbol, exchange, open, close, high, low, and volume.
                            val marketData = marketDataList[0]
                            val symbol = marketData.symbol
                            val exchange = marketData.exchange
                            val open = marketData.open
                            val close = marketData.close
                            val high = marketData.high
                            val low = marketData.low
                            val volume = marketData.volume

                            // Update the TextViews
                            val google = rootView.findViewById<TextView>(R.id.symbol2)
                            val googleex = rootView.findViewById<TextView>(R.id.exchange2)
                            val googleop = rootView.findViewById<TextView>(R.id.open2)
                            val googlecl = rootView.findViewById<TextView>(R.id.close2)
                            val googleehi = rootView.findViewById<TextView>(R.id.high2)
                            val googlelo = rootView.findViewById<TextView>(R.id.low2)
                            val googlevo = rootView.findViewById<TextView>(R.id.volume2)

                            google.text = "Google Inc. - $symbol"
                            googleex.text = "$exchange Exchange"
                            googleop.text = "Open: $open"
                            googlecl.text = "Close: $close"
                            googleehi.text = "High: $high"
                            googlelo.text = "Low: $low"
                            googlevo.text = "Volume: $volume"
                        }
                    }
                } else {
                    // Log the API error
                    Log.e("InvestingFragment", "API call failed: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MarketStackResponse>, t: Throwable) {
                Log.e("InvestingFragment", "API call failed: ${t.message}")
            }
        })

    }

    private fun fetchappledata(rootView: View) {
        val baseUrl = "http://api.marketstack.com/v1/"
        val accessKey = "4794206a750f0b853e937da6726c67c3"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val marketStackService = retrofit.create(MarketStackApiService::class.java)
        val symbols = "AAPL"

        val call: Call<MarketStackResponse> = marketStackService.getMarketData(accessKey, symbols)
        call.enqueue(object : Callback<MarketStackResponse> {
            override fun onResponse(call: Call<MarketStackResponse>, response: Response<MarketStackResponse>) {
                if (response.isSuccessful) {
                    val marketDataResponse = response.body()
                    if (marketDataResponse != null) {
                        val marketDataList = marketDataResponse.data
                        if (marketDataList.isNotEmpty()) {
                            // Use the marketDataList[0] (first item) here to access the symbol, exchange, open, close, high, low, and volume.
                            val marketData = marketDataList[0]
                            val symbol = marketData.symbol
                            val exchange = marketData.exchange
                            val open = marketData.open
                            val close = marketData.close
                            val high = marketData.high
                            val low = marketData.low
                            val volume = marketData.volume

                            // Update the TextViews
                            val apple = rootView.findViewById<TextView>(R.id.symbol1)
                            val appleex = rootView.findViewById<TextView>(R.id.exchange1)
                            val appleop = rootView.findViewById<TextView>(R.id.open1)
                            val applecl = rootView.findViewById<TextView>(R.id.close1)
                            val applehi = rootView.findViewById<TextView>(R.id.high1)
                            val applelo = rootView.findViewById<TextView>(R.id.low1)
                            val applevo = rootView.findViewById<TextView>(R.id.volume1)

                            apple.text = "Apple Inc. - $symbol"
                            appleex.text = "$exchange Exchange"
                            appleop.text = "Open: $open"
                            applecl.text = "Close: $close"
                            applehi.text = "High: $high"
                            applelo.text = "Low: $low"
                            applevo.text = "Volume: $volume"
                        }
                    }
                } else {
                    // Log the API error
                    Log.e("InvestingFragment", "API call failed: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MarketStackResponse>, t: Throwable) {
                Log.e("InvestingFragment", "API call failed: ${t.message}")
            }
        })
    }
}
