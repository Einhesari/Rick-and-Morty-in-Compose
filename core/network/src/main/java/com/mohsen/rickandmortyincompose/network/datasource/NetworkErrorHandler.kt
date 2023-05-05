package com.mohsen.rickandmortyincompose.network.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

object NetworkErrorHandler {
    suspend fun <T> apiCall(api: suspend () -> Response<T>): T = withContext(Dispatchers.IO) {
        try {
            return@withContext processResponse(api.invoke())
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is NoInternetException -> throw NoInternetException()
                is IOException -> throw ServerOutOfReachException()
                else -> throw UnknownException()
            }
        }
    }


    private fun <T> processResponse(response: Response<T>): T =
        if (response.isSuccessful) response.body() ?: throw UnknownException()
        else {
            response.errorBody()?.let {
                throw IOException(it.string())
            } ?: run {
                throw  UnknownException()
            }
        }
}


class NoInternetException(msg: String = "No internet connection") : IOException(msg)
class ServerOutOfReachException(msg: String = "Can not connect to the server") : IOException(msg)
class UnknownException(msg: String = "An unknown exception occurred") : IOException(msg)