package com.sportstracking.helmetly.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnectivityChecker(val context: Context) {

    fun checkForInternet(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected == true
    }
}