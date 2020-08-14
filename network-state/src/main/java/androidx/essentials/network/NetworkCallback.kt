package androidx.essentials.network

import android.content.Context
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.essentials.network.connectivity.ConnectivityManager

class NetworkCallback(private val context: Context) {

    private var currentNetworkState: NetworkState? = null
        set(value) {
            field = value
            value?.name?.let { Log.d(javaClass.simpleName, it) }
        }

    private val networkBroadcastReceiver = NetworkCallbackReceiver()

    private val networkCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        object : android.net.ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (currentNetworkState != NetworkState.AVAILABLE) {
                    currentNetworkState = NetworkState.AVAILABLE
                    super.onAvailable(network)
                    IS_ONLINE = true
                }
            }

            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                if (currentNetworkState != NetworkState.BLOCKED_STATUS_CHANGED) {
                    currentNetworkState = NetworkState.BLOCKED_STATUS_CHANGED
                    super.onBlockedStatusChanged(network, blocked)
                }
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                if (currentNetworkState != NetworkState.CAPABILITIES_CHANGED) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    currentNetworkState = NetworkState.CAPABILITIES_CHANGED
                }
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                if (currentNetworkState != NetworkState.LINK_PROPERTIES_CHANGED) {
                    currentNetworkState = NetworkState.LINK_PROPERTIES_CHANGED
                    super.onLinkPropertiesChanged(network, linkProperties)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                if (currentNetworkState != NetworkState.LOSING) {
                    currentNetworkState = NetworkState.LOSING
                    super.onLosing(network, maxMsToLive)
                }
            }

            override fun onLost(network: Network) {
                if (currentNetworkState != NetworkState.LOST) {
                    currentNetworkState = NetworkState.LOST
                    super.onLost(network)
                    IS_ONLINE = false
                }
            }

            override fun onUnavailable() {
                if (currentNetworkState != NetworkState.UNAVAILABLE) {
                    currentNetworkState = NetworkState.UNAVAILABLE
                    super.onUnavailable()
                }
            }
        }
    } else null

    init {
        connectivityManager = ConnectivityManager.getInstance(context)
    }

    private fun register() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.registerNetworkCallback(
                NetworkRequest.Builder().apply {
                    addTransportType(NetworkCapabilities.TRANSPORT_VPN)
                    addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                    addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                }.build(), networkCallback!!
            )
        } else {
            networkBroadcastReceiver.onNetworkStateChangeListener = onNetworkStateChangeListener
            context.registerReceiver(
                networkBroadcastReceiver,
                NetworkCallbackReceiver.intentFilter
            )
        }
    }

    fun setOnNetworkStateChangeListener(onNetworkStateChangeListener: OnNetworkStateChangeListener?) {
        Companion.onNetworkStateChangeListener = onNetworkStateChangeListener
        register()
    }

    fun setOnNetworkStateChangeListener(action: (Boolean) -> Unit) {
        onNetworkStateChangeListener = object : OnNetworkStateChangeListener {
            override fun onNetworkStateChange(isOnline: Boolean) {
                action(isOnline)
            }
        }
        register()
    }

    fun removeListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.unregisterNetworkCallback(networkCallback!!)
        } else {
            context.unregisterReceiver(networkBroadcastReceiver)
        }
    }

    interface OnNetworkStateChangeListener {
        fun onNetworkStateChange(isOnline: Boolean)
    }

    companion object {

        var IS_ONLINE = false
            private set(value) {
                field = value
                onNetworkStateChangeListener?.onNetworkStateChange(value)
            }

        private var connectivityManager: android.net.ConnectivityManager? = null
        private var onNetworkStateChangeListener: OnNetworkStateChangeListener? = null

    }
}