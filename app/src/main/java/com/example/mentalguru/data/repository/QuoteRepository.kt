package com.example.mentalguru.data.repository

import android.util.Log
import com.example.mentalguru.data.remote.UnsafeOkHttpClient
import com.example.mentalguru.data.remote.QuoteApiService
import com.example.mentalguru.data.model.Quote
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteRepository {

    private val api: QuoteApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.quotable.io/")
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(QuoteApiService::class.java)
    }

    suspend fun fetchRandomQuote(): Quote? {
        return try {
            api.getRandomQuote() as Quote
        } catch (e: Exception) {
            Log.e("QuoteRepository", "Error fetching quote", e)
            null
        }
    }


    suspend fun fetchRandomQuoteByTag(tag: String): Quote? {
        return try {
            api.getRandomQuoteByTag(tag) as Quote
        } catch (e: Exception) {
            Log.e("QuoteRepository", "Error fetching quote by tag", e)
            null
        }
    }
}
