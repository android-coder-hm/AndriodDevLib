package com.developlib.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.developlib.ConfigSingleton

//网络是否连接
fun isNetworkConnect(): Boolean {
    val connectivityManager = ConfigSingleton.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (null != networkCapabilities) {
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable
    }

    return false
}