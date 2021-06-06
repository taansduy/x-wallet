package com.example.mywallet.model

import java.net.SocketTimeoutException

data class Result<T : Any>(
    val status: ResultStatus,
    val data: T? = null,
    val message: String? = null,
    val error: Exception? = null
) {
    companion object {
        fun <T : Any> success(data: T? = null): Result<T> = Result(ResultStatus.SUCCESS, data)

        fun <T : Any> error(error: Exception?): Result<T> =
            when (error) {
                is SocketTimeoutException -> Result(
                    ResultStatus.ERROR,
                    null,
                    "Connection with Main station has problem. Please check connection again.",
                    error
                )
                else -> Result(ResultStatus.ERROR, null, null, error)
            }

        fun <T : Any> loading(message: String? = null): Result<T> =
            Result(ResultStatus.LOADING, null, message)
    }

    val isLoading: Boolean
        get() = status == ResultStatus.LOADING

    fun requireData(): T = data ?: throw Exception("Data is not available")
}


enum class ResultStatus {
    SUCCESS,
    ERROR,
    LOADING
}