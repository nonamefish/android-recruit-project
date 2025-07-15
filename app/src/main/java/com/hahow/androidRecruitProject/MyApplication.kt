package com.hahow.androidRecruitProject

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext

        startKoin {
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }

    companion object {
        lateinit var instance: Application
        lateinit var appContext: Context
            private set
    }
}