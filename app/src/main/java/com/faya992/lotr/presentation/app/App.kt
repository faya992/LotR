package com.faya992.lotr.presentation.app

import android.app.Application
import com.faya992.lotr.presentation.di.appModule
import com.faya992.lotr.presentation.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(appModule, dataModule))
        }
    }
}