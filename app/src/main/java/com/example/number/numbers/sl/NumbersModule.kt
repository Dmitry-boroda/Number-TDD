package com.example.number.numbers.sl

import com.example.number.main.sl.Core
import com.example.number.main.sl.Module
import com.example.number.numbers.data.BaseNumbersRepository
import com.example.number.numbers.data.HandleDataRequest
import com.example.number.numbers.data.HandleDomainError
import com.example.number.numbers.data.NumberDataToDomain
import com.example.number.numbers.data.cache.NumberDataToCache
import com.example.number.numbers.data.cache.NumbersCacheDataSource
import com.example.number.numbers.data.cloud.NumbersCloudDataSource
import com.example.number.numbers.data.cloud.NumbersService
import com.example.number.numbers.domain.*
import com.example.number.numbers.presentation.*

class NumbersModule(
    private val core: Core,
    private val provideRepository: ProvideNumbersRepository
) : Module<NumberViewModel.Base> {
    override fun viewModel(): NumberViewModel.Base {
        val repository = provideRepository.provideRepository()
        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            NumbersStateCommunication.Base(),
            NumbersListCommunication.Base(),
        )

        return NumberViewModel.Base(
            HandelNumbersRequest.Base(
                core.provideDispatchers(),
                communications,
                NumbersResultMapper(communications, NumberUiMapper()),
            ),
            core,
            communications,
            NumbersInteractor.Base(
                repository,
                HandleRequest.Base(
                    HandleError.Base(core),
                    repository
                ),
                core.provideNumberDetails()
            ),
            core.provideNavigation(),
            DetailsUi()
        )
    }
}

interface ProvideNumbersRepository {
    fun provideRepository(): NumbersRepository
    class Base(private val core: Core) : ProvideNumbersRepository {
        override fun provideRepository(): NumbersRepository {
            val cacheDataSource = NumbersCacheDataSource.Base(
                core.provideDataBase().numbersDao(),
                NumberDataToCache()
            )
            return BaseNumbersRepository(
                NumbersCloudDataSource.Base(
                    core.service(NumbersService::class.java)
                ),
                cacheDataSource,
                HandleDataRequest.Base(
                    cacheDataSource,
                    NumberDataToDomain(),
                    HandleDomainError()
                ),
                NumberDataToDomain()
            )
        }

    }
}