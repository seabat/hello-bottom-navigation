package dev.seabat.android.hellobottomnavi

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: Context
        fun getApplicationContext(): Context = instance.applicationContext
    }
}
