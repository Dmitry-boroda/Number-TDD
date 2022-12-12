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

class NumbersModule(private val core: Core) : Module<NumberViewModel> {
    override fun viewModel(): NumberViewModel {
        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            NumbersStateCommunication.Base(),
            NumbersListCommunication.Base(),
        )
        val cacheDataSource = NumbersCacheDataSource.Base(
            core.provideDataBase().numbersDao(),
            NumberDataToCache()
        )
        val numbersRepository = BaseNumbersRepository(
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
        return NumberViewModel(
            HandelNumbersRequest.Base(
                core.provideDispatchers(),
                communications,
                NumbersResultMapper(communications, NumberUiMapper()),
            ),
            core,
            communications,
            NumbersInteractor.Base(
                numbersRepository,
                HandleRequest.Base(
                    HandleError.Base(core),
                    numbersRepository
                )
            )
        )
    }
}