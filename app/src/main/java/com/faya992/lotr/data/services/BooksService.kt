package com.faya992.lotr.data.services

import com.faya992.lotr.data.models.BooksRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BooksService {

    companion object {
        val bookOne = "5cf5805fb53e011a64671582"
        val bookTwo = "5cf58077b53e011a64671583"
        val bookThree = "5cf58080b53e011a64671584"
    }

    @Headers("Authorization: Bearer -CT6umrl6otfZKhuOJAv")
    @GET("/v2/book/{id}/chapter")
    suspend fun getBooks(@Path("id") id: String): Response<BooksRemote>
}