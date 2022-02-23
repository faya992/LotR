package com.faya992.lotr.presentation.di
import com.faya992.lotr.data.network.*
import org.koin.dsl.module

val dataModule = module {
    factory { provideOkHttpClient(get()) }
    factory { getCharactersService(get()) }
    factory { getQuotesService(get()) }
    factory { getMoviesService(get()) }
    factory { getBooksService(get()) }
    factory { okHttpInterceptor() }
    single { provideRetrofit(get()) }
}

