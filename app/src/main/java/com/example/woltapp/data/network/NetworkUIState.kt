package com.example.woltapp.data.network

sealed class NetworkUiState<out T> {
    object Loading : NetworkUiState<Nothing>()
    data class Success<out T>(val data: T) : NetworkUiState<T>()
    data class Error(val exception: Throwable) : NetworkUiState<Nothing>()
}