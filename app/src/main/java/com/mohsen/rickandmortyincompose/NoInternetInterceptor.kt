package com.mohsen.rickandmortyincompose

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoInternetInterceptor @Inject constructor(private val connectionChecker: ConnectionChecker) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionChecker.isConnectionOn()) chain.proceed(chain.request())
        else throw  NoInternetException()
    }
}