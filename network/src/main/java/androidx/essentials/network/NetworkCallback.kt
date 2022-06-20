package androidx.essentials.network

import android.net.ConnectivityManager
import android.net.Network

internal class NetworkCallback(
    private val callback: ((Boolean) -> Unit)? = null
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        callback?.invoke(true)
    }

    override fun onLost(network: Network) {
        callback?.invoke(false)
        super.onLost(network)
    }

    override fun onUnavailable() {
        callback?.invoke(false)
        super.onUnavailable()
    }

}