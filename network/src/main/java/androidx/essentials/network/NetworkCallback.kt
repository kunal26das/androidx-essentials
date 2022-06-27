package androidx.essentials.network

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData

class NetworkCallback private constructor() {

    companion object : ConnectivityManager.NetworkCallback() {

        val isNetworkAvailable: LiveData<Boolean> get() = IsNetworkAvailable

        override fun onAvailable(network: Network) {
            IsNetworkAvailable.postValue(true)
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            IsNetworkAvailable.postValue(false)
            super.onLost(network)
        }

        override fun onUnavailable() {
            IsNetworkAvailable.postValue(false)
            super.onUnavailable()
        }
    }

}