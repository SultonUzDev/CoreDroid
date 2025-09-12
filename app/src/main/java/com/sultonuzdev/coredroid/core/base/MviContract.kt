package com.sultonuzdev.coredroid.core.base


interface MviContract {
    interface State
    interface Intent
    interface Effect
}

// Enhanced LoadingState with more options
data class LoadingInfo(
    val isLoading: Boolean = false,
    val message: String? = null,
    val progress: Float? = null // For progress indicators
) {
    companion object {
        fun loading(message: String? = null) = LoadingInfo(isLoading = true, message = message)
        fun idle() = LoadingInfo(isLoading = false)
        fun progress(progress: Float, message: String? = null) =
            LoadingInfo(isLoading = true, message = message, progress = progress)
    }
}

// Enhanced UiState with more specific error handling
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(
        val exception: Throwable,
        val message: String = exception.message ?: "Unknown error"
    ) : UiState<Nothing>()
    object Empty : UiState<Nothing>()

    // Utility functions
    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isEmpty: Boolean get() = this is Empty

    fun getDataOrNull(): T? = if (this is Success) data else null
    fun getErrorOrNull(): Throwable? = if (this is Error) exception else null
}



// Extension functions for easier usage
inline fun <T> UiState<T>.onSuccess(action: (T) -> Unit): UiState<T> {
    if (this is UiState.Success) action(data)
    return this
}

inline fun <T> UiState<T>.onError(action: (Throwable) -> Unit): UiState<T> {
    if (this is UiState.Error) action(exception)
    return this
}

inline fun <T> UiState<T>.onLoading(action: () -> Unit): UiState<T> {
    if (this is UiState.Loading) action()
    return this
}