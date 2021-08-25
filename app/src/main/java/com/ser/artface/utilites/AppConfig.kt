package com.ser.artface.utilites

import android.app.Application
import com.ser.artface.dependencyinjection.repositoryModule
import com.ser.artface.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
@Suppress("unused")
class AppConfig :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}