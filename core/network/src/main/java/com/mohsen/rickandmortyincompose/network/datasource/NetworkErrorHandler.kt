package com.mohsen.rickandmortyincompose.network.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

object NetworkErrorHandler {
    suspend fun <T> safeApiCall(api: suspend () -> Response<T>): Result<T> = withContext(Dispatchers.IO) {
        try {
            return@withContext Result.success(processResponse(api.invoke()))
        } catch (e: Exception) {
            return@withContext when (e) {
                is UnknownHostException, is NoInternetException -> Result.failure(Exception("No internet connection"))
                is IOException -> Result.failure(Exception("Can not connect to the server"))
                else -> Result.failure(Exception("An unknown exception occurred"))
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
class UnknownException(msg: String = "An unknown exception occurred") : IOException(msg)