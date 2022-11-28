package com.example.number.numbers.data

import com.example.number.numbers.domain.HandleError
import com.example.number.numbers.domain.NoInternetConnectException
import com.example.number.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError:HandleError<Exception> {
    override fun handle(e: Exception): Exception {
        return when(e){
            is UnknownHostException -> NoInternetConnectException()
            else -> ServiceUnavailableException()
        }
    }
}