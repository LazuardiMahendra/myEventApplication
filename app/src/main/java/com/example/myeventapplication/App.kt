package com.example.myeventapplication

import android.app.Application
import com.example.myeventapplication.di.networkModule
import com.example.myeventapplication.di.prefModule
import com.example.myeventapplication.di.repoModule
import com.example.myeventapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                networkModule,
                prefModule,
                repoModule,
                viewModelModule
            )
        }
    }
}