package `in`.hahow.androidRecruitProject

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application
        val appContext: Context = instance.applicationContext
            private set
    }
}