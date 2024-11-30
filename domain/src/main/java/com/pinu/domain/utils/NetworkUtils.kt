package com.pinu.domain.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry


object NetworkUtils {

    /*
    API will be executed in a background thread.
    If a retry count is set, API will retry automatically on failure.
     */
    fun <T> safeApiCall(retryCount: Long = 1, apiCall: suspend () -> T)
            : Flow<Result<T>> = flow {
        emit(Result.success(apiCall()))
    }.flowOn(Dispatchers.IO)
        .retry(retryCount)
        .catch { error -> emit(Result.failure(error)) }

}