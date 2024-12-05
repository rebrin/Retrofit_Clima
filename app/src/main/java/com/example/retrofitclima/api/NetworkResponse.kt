package com.example.retrofitclima.api

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data:T): NetworkResponse<T>()
    data class Error(val mensaje: String): NetworkResponse<Nothing>()
    object Loading: NetworkResponse<Nothing>()
}