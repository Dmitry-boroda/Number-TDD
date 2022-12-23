package com.example.number.random

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.number.numbers.domain.RandomNumberRepository

class PeriodicRandomWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val repository =
                (applicationContext as ProvidePeriodicRepository).providePeriodicRepository()
            repository.randomNumberFact()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

interface ProvidePeriodicRepository {
    fun providePeriodicRepository(): RandomNumberRepository
}