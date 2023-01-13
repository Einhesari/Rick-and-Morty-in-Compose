package com.mohsen.rickandmortyincompose

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class ConnectionChecker @Inject constructor(private val connectivityManager: ConnectivityManager) {

    fun isConnectionOn(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            postAndroidMInternetCheck()
        } else {
            preAndroidMInternetCheck()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun postAndroidMInternetCheck(): Boolean {
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)

        return connection != null && (connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || connection.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ) || connection.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
    }

    private fun preAndroidMInternetCheck(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI || activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }
}