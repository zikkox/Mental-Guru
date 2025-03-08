package com.example.mentalguru.data.remote

import com.example.mentalguru.data.model.Music
import retrofit2.http.GET

interface MusicApiService {
    @GET("musics")
    suspend fun getMusicList(): List<Music>

    @GET("musics/{id}")
    suspend fun getMusicById(@retrofit2.http.Path("id") id: String): Music?
}
