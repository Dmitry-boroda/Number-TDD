package com.example.number.numbers.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface CloudModule {

    fun <T> service(clasz: Class<T>): T

    abstract class Abstract : CloudModule {

        protected abstract val level: HttpLoggingInterceptor.Level
        protected abstract val baseUrl: String
        override fun <T> service(clasz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(level)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return retrofit.create(clasz)
        }
    }

    class Release : Abstract() {
        override val level = HttpLoggingInterceptor.Level.NONE
        override val baseUrl = "http://numbersapi.com/"
    }

    class Debug : Abstract() {
        override val level = HttpLoggingInterceptor.Level.BODY
        override val baseUrl = "http://numbersapi.com/"
    }
}