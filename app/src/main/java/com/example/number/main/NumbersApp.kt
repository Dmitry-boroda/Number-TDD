package com.example.number.main

import android.app.Application
import com.example.number.BuildConfig
import com.example.number.numbers.data.cloud.CloudModule

class NumbersApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // todo move out of her
        val cloudDataSource = if (BuildConfig.DEBUG)
            CloudModule.Debug()
        else
            CloudModule.Release()
    }
}