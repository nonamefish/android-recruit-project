package `in`.hahow.androidRecruitProject

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
    }

    companion object {
        lateinit var instance: Application
        lateinit var appContext: Context
            private set
    }
}