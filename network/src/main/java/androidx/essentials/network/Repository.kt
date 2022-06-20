package androidx.essentials.network

import android.net.ConnectivityManager
import android.net.NetworkRequest
import java.io.Closeable

abstract class Repository(
    private val connectivityManager: ConnectivityManager? = null,
    private val networkRequest: NetworkRequest? = null,
) : Closeable {

    var isNetworkAvailable = true
        @Synchronized private set
        @Synchronized get

    private val networkCallback = NetworkCallback { isNetworkAvailable = it }

    init {
        if (networkRequest != null) {
            connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    override fun close() {
        if (networkRequest != null) {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }

    suspend fun <T> execute(block: suspend () -> T): T {
        when (isNetworkAvailable) {
            true -> return block.invoke()
            false -> throw NetworkUnavailableException()
        }
    }

}