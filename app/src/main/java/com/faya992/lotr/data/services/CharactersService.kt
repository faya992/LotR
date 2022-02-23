package com.faya992.lotr.data.services

import com.faya992.lotr.data.models.CharactersRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CharactersService {

    @Headers("Authorization: Bearer -CT6umrl6otfZKhuOJAv")
    @GET("character/")
    suspend fun getCharacters(): Response<CharactersRemote>

    @Headers("Authorization: Bearer -CT6umrl6otfZKhuOJAv")
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): Response<CharactersRemote>


}