package com.example.listusertestapp

import android.app.Application
import com.example.listusertestapp.di.appModule
import com.example.listusertestapp.di.networkModule
import org.koin.core.context.GlobalContext.startKoin

/**
 * Created by Taranjeet Singh on 15/10/25.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule)
            modules(appModule)
        }
    }
}