package com.example.number.main.sl

import android.content.Context
import com.example.number.details.data.NumberFactDetails
import com.example.number.main.presentation.NavigationCommunication
import com.example.number.numbers.data.cache.CacheModule
import com.example.number.numbers.data.cache.NumbersDataBase
import com.example.number.numbers.data.cloud.CloudModule
import com.example.number.numbers.presentation.DispatchersList
import com.example.number.numbers.presentation.ManagerResources
import com.example.number.random.WorkManagerWrapper

interface Core : CloudModule, CacheModule, ManagerResources, ProvideNavigation,
    ProvideNumberDetails, ProvideWorkManagerWrapper {

    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val providerInstances: ProvideInstances,
    ) : Core {
        private val workManagerWrapper: WorkManagerWrapper = WorkManagerWrapper.Base(context)
        private val numberDetails = NumberFactDetails.Base()
        private val navigationCommunication = NavigationCommunication.Bass()
        private val managerResources: ManagerResources = ManagerResources.Base(context)
        private val dispatchersList by lazy {
            DispatchersList.Base()
        }
        private val cloudModule by lazy {
            providerInstances.provideCloudModule()
        }

        private val cacheModule by lazy {
            providerInstances.provideCacheModule()
        }

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)
        override fun provideDataBase(): NumbersDataBase = cacheModule.provideDataBase()

        override fun string(id: Int): String = managerResources.string(id)
        override fun provideNavigation(): NavigationCommunication.Mutable = navigationCommunication
        override fun provideNumberDetails(): NumberFactDetails.Mutable = numberDetails
        override fun provideWorkManagerWrapper() = workManagerWrapper

        override fun provideDispatchers(): DispatchersList = dispatchersList
    }
}

interface ProvideWorkManagerWrapper {
    fun provideWorkManagerWrapper(): WorkManagerWrapper
}

interface ProvideNavigation {
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideNumberDetails {
    fun provideNumberDetails(): NumberFactDetails.Mutable
}