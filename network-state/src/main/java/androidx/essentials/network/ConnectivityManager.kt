package androidx.essentials.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

object ConnectivityManager {

    private var connectivityManager: ConnectivityManager? = null

    fun getInstance(context: Context): ConnectivityManager? {
        if (connectivityManager != null) {
            return connectivityManager
        }
        synchronized(this) {
            connectivityManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getSystemService(ConnectivityManager::class.java)
            } else {
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            }
            return connectivityManager
        }
    }
}