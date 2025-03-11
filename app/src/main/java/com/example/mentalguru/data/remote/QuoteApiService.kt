package com.example.mentalguru.data.remote

import retrofit2.Call
import com.example.mentalguru.data.model.Quote
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApiService {

    @GET("random")
    suspend fun getRandomQuote(): Quote

    @GET("random")
    suspend fun getRandomQuoteByTag(@Query("tags") tags: String): Quote

}