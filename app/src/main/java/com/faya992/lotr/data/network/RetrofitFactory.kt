package com.faya992.lotr.data.network

import com.faya992.lotr.data.services.BooksService
import com.faya992.lotr.data.services.CharactersService
import com.faya992.lotr.data.services.MoviesService
import com.faya992.lotr.data.services.QuotesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun okHttpInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}

fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://the-one-api.dev/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getCharactersService(retrofit: Retrofit): CharactersService = retrofit.create(CharactersService::class.java)
fun getQuotesService(retrofit: Retrofit): QuotesService = retrofit.create(QuotesService::class.java)
fun getMoviesService(retrofit: Retrofit): MoviesService = retrofit.create(MoviesService::class.java)
fun getBooksService(retrofit: Retrofit): BooksService = retrofit.create(BooksService::class.java)

