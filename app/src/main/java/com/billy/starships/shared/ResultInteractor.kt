package com.billy.starships.shared

abstract class ResultInteractor<in P, R> {
    suspend operator fun invoke(params: P): R {
        return doWork(params)
    }

    protected abstract suspend fun doWork(params: P): R
}