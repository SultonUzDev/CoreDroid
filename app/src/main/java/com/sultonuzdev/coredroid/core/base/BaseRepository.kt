package com.sultonuzdev.coredroid.core.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
abstract class BaseRepository {

    // 🛡️ Safe API calls with retry
    protected inline fun <T> safeApiCall(
        retries: Int = 2,
        crossinline apiCall: suspend () -> T
    ): Flow<Result<T>> = flow {
        var lastException: Exception? = null

        repeat(retries) { attempt ->
            try {
                val result = apiCall()
                emit(Result.success(result))
                return@flow
            } catch (e: Exception) {
                lastException = e
                Timber.w(e, "API call failed, attempt ${attempt + 1}/$retries")
            }
        }

        Timber.e(lastException, "API call failed after $retries attempts")
        emit(Result.failure(lastException!!))
    }

    // 🎯 CORRECT VERSION - Helper for single Flow operations with fallback
    protected fun <T> safeFlowCall(
        fallback: T? = null,  // ← KEY DIFFERENCE: Fallback parameter
        flowCall: suspend () -> Flow<T>
    ): Flow<T> = flow {
        safeApiCall {
            flowCall().first()
        }.collect { result ->
            result.onSuccess { data ->
                emit(data)
            }.onFailure { error ->
                fallback?.let {
                    emit(it) // ← Emit fallback instead of throwing
                } ?: throw error
            }
        }
    }

    // 🔄 Helper for monitoring operations with fallback
    protected fun <T> safeMonitoring(
        intervalMs: Long,
        flowCall: suspend () -> Flow<T>,
        onError: (Throwable) -> T? = { null }
    ): Flow<T> = flow {
        while (true) {
            safeApiCall {
                flowCall().first()
            }.collect { result ->
                result.onSuccess { data ->
                    emit(data)
                }.onFailure { error ->
                    onError(error)?.let { fallbackData ->
                        emit(fallbackData)
                    }
                }
            }
            delay(intervalMs)
        }
    }
}
