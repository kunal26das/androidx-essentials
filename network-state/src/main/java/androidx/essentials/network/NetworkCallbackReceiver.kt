package androidx.essentials.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
@Deprecated("DEPRECATION")
class NetworkCallbackReceiver : BroadcastReceiver() {

    private var connectivityManager: ConnectivityManager? = null
    var onNetworkStateChangeListener: NetworkCallback.OnNetworkStateChangeListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        connectivityManager =
            connectivityManager ?: ConnectivityManagerCompat.getInstance(context!!)
        onNetworkStateChangeListener?.onNetworkStateChange(
            connectivityManager?.activeNetworkInfo?.isConnected ?: false
        )
    }

    companion object {
        val intentFilter = IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        }
    }
}
