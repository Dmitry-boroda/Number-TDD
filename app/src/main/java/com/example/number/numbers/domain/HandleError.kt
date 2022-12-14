package com.example.number.numbers.domain

import com.example.number.R
import com.example.number.numbers.presentation.ManagerResources

interface HandleError<T> {
    fun handle(e: Exception): T
    class Base(private val managerResources: ManagerResources) : HandleError<String> {
        override fun handle(e: Exception) = managerResources.string(
            when (e) {
                is NoInternetConnectException -> R.string.no_connection_message
                else -> R.string.service_is_unavailable
            }
        )
    }
}