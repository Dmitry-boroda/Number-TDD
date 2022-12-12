package com.example.number.main.sl

import android.content.Context
import com.example.number.numbers.data.cache.CacheModule
import com.example.number.numbers.data.cache.NumbersDataBase
import com.example.number.numbers.data.cloud.CloudModule
import com.example.number.numbers.presentation.DispatchersList
import com.example.number.numbers.presentation.ManagerResources

interface Core : CloudModule, CacheModule, ManagerResources {

    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val isRelease: Boolean,
    ) : Core {
        private val managerResources: ManagerResources = ManagerResources.Base(context)
        private val dispatchersList by lazy {
            DispatchersList.Base()
        }
        private val cloudModule by lazy {
            if (isRelease)
                CloudModule.Release()
            else
                CloudModule.Debug()
        }

        private val cacheModule by lazy {
            if (isRelease)
                CacheModule.Base(context)
            else
                CacheModule.Mock(context)
        }

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)
        override fun provideDataBase(): NumbersDataBase = cacheModule.provideDataBase()

        override fun string(id: Int): String = managerResources.string(id)
        override fun provideDispatchers(): DispatchersList = dispatchersList
    }
}