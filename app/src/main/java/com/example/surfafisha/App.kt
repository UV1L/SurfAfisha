package com.example.surfafisha

import android.app.Application
import com.example.data.DB.DbProvider

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DbProvider.initialize(applicationContext)
    }
}