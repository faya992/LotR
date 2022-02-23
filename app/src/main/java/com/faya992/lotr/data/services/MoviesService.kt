package com.faya992.lotr.data.services

import com.faya992.lotr.data.models.MoviesRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MoviesService {

    companion object {
        val movieOne = "5cd95395de30eff6ebccde5a"
        val movieTwo = "5cd95395de30eff6ebccde5b"
        val movieThree = "5cd95395de30eff6ebccde5c"

    }

    @Headers("Authorization: Bearer -CT6umrl6otfZKhuOJAv")
    @GET("movie/{id}")
    suspend fun getMovies(@Path("id") id: String): Response<MoviesRemote>
}
