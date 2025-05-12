package com.droidcon.myandroidapp.jokes.data.network

import com.droidcon.myandroidapp.jokes.data.model.JokeDto
import retrofit2.http.GET

interface JokesApiService {
    @GET("/random_ten")
    suspend fun getRandomJokes(): List<JokeDto>
}