package com.example.bookapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BookApp)
            modules(appModule)
        }
    }
}