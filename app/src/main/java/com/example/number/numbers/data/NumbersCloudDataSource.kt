package com.example.number.numbers.data

import java.net.UnknownHostException

interface NumbersCloudDataSource : FetchNumber{

    suspend fun randomNumber(): NumberData
}