package com.example.number.numbers.presentation

import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.number.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandelNumbersRequest {

    fun handel(
        coroutineScope: CoroutineScope,
        block: suspend () -> NumbersResult
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communications: NumbersCommunications,
        private val numbersResultMapper: NumbersResult.Mapper<Unit>,
    ) : HandelNumbersRequest {
        override fun handel(
            coroutineScope: CoroutineScope,
            block: suspend () -> NumbersResult
        ) {
            communications.showProgress(View.VISIBLE)
            coroutineScope.launch(dispatchers.io()) {
                val result = block.invoke()
                communications.showProgress(View.GONE)
                result.map(numbersResultMapper)
            }
        }

    }
}