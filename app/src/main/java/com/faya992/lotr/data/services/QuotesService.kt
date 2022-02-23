package com.faya992.lotr.data.services


import com.faya992.lotr.data.models.QuotesRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuotesService {

    @Headers("Authorization: Bearer -CT6umrl6otfZKhuOJAv")
    @GET("quote/")
    suspend fun getQuotes(): Response<QuotesRemote>
}
