package com.example.mentalguru.data.repository

import android.util.Log
import com.example.mentalguru.data.remote.MusicApiService
import com.example.mentalguru.data.model.Music
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicRepository {

    private val api: MusicApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tekla-api.sabaapkhazava.ge/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MusicApiService::class.java)
    }

    suspend fun fetchMusicList(): List<Music> {
        return try {
            api.getMusicList()
        } catch (e: Exception) {
            Log.e("MusicRepository", "Error fetching music list", e)
            emptyList()
        }
    }

    suspend fun fetchMusicById(id: String): Music? {
        return try {
            api.getMusicById(id)
        } catch (e: Exception) {
            Log.e("MusicRepository", "Error fetching music by ID", e)
            null
        }
    }
}
