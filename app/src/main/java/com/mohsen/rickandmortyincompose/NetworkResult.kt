package com.mohsen.rickandmortyincompose

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val errMsg: String) : NetworkResult<Nothing>()
}