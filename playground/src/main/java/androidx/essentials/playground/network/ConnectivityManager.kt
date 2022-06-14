package androidx.essentials.playground.network

import android.content.Context
import android.net.ConnectivityManager

object ConnectivityManager {

    private var connectivityManager: ConnectivityManager? = null

    fun getInstance(context: Context): ConnectivityManager {
        if (connectivityManager != null) {
            return connectivityManager!!
        }
        synchronized(this) {
            connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            return connectivityManager!!
        }
    }

}