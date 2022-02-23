package com.faya992.lotr.data.network

import com.faya992.lotr.data.services.BooksService
import com.faya992.lotr.data.services.CharactersService
import com.faya992.lotr.data.services.MoviesService
import com.faya992.lotr.data.services.QuotesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

class RetrofitFactory {

    private fun okHttpInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(okHttpInterceptor())
        .build()

    private val retrofitClient: Retrofit = Retrofit.Builder()
        .baseUrl("https://the-one-api.dev/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getCharactersService(): CharactersService = retrofitClient.create(CharactersService::class.java)

    fun getQuotesService(): QuotesService = retrofitClient.create(QuotesService::class.java)

    fun getMoviesService(): MoviesService = retrofitClient.create(MoviesService::class.java)

    fun getBooksService(): BooksService = retrofitClient.create(BooksService::class.java)

}