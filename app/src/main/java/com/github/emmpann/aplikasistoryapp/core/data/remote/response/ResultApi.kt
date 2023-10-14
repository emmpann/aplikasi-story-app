package com.github.emmpann.aplikasistoryapp.core.data.remote.response

sealed class ResultApi<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultApi<T>()
    data class Error(val error: String) : ResultApi<Nothing>()
    object Loading : ResultApi<Nothing>()
}