package com.example.number.main.sl

import android.content.Context
import com.example.number.numbers.data.cache.CacheModule
import com.example.number.numbers.data.cloud.CloudModule

interface ProvideInstances {

    fun provideCloudModule(): CloudModule
    fun provideCacheModule(): CacheModule



    class Realise(private val context: Context) : ProvideInstances {
        override fun provideCloudModule() = CloudModule.Base()
        override fun provideCacheModule() = CacheModule.Base(context)
    }

    class Mock(private val context: Context) : ProvideInstances {
        override fun provideCloudModule() = CloudModule.Mock()
        override fun provideCacheModule() = CacheModule.Mock(context)
    }
}