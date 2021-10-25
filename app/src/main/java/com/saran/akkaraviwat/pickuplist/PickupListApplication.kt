package com.saran.akkaraviwat.pickuplist

import android.app.Application
import com.saran.akkaraviwat.pickuplist.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PickupListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(AppModule.allModules)
        }
    }
}
