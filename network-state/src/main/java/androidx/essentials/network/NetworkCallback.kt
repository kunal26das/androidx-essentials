package androidx.essentials.network

import android.content.Context
import android.content.IntentFilter
import android.net.*
import android.os.Build
import android.util.Log
import androidx.essentials.network.NetworkCallbackReceiver.Companion.CONNECTIVITY_ACTION

class NetworkCallback(private val context: Context) {

    var isOnline = false
    private var currentNetworkState: NetworkState? = null
    private val networkBroadcastReceiver = NetworkCallbackReceiver()
    private var onNetworkStateChangeListener: OnNetworkStateChangeListener? = null
        set(value) {
            field = value
            networkBroadcastReceiver.onNetworkStateChangeListener = value
        }
    private val connectivityManager = ConnectivityManagerCompat.getInstance(context)
    private val networkCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (currentNetworkState != NetworkState.AVAILABLE) {
                    onNetworkStateChangeListener?.onOnline()
                    log(NetworkState.AVAILABLE)
                    super.onAvailable(network)
                }
            }

            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                if (currentNetworkState != NetworkState.BLOCKED_STATUS_CHANGED) {
                    super.onBlockedStatusChanged(network, blocked)
                    log(NetworkState.BLOCKED_STATUS_CHANGED)
                }
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                if (currentNetworkState != NetworkState.CAPABILITIES_CHANGED) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    log(NetworkState.CAPABILITIES_CHANGED)
                }
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                if (currentNetworkState != NetworkState.LINK_PROPERTIES_CHANGED) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    log(NetworkState.LINK_PROPERTIES_CHANGED)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                if (currentNetworkState != NetworkState.LOSING) {
                    super.onLosing(network, maxMsToLive)
                    log(NetworkState.LOSING)
                }
            }

            override fun onLost(network: Network) {
                if (currentNetworkState != NetworkState.LOST) {
                    onNetworkStateChangeListener?.onOffline()
                    log(NetworkState.LOST)
                    super.onLost(network)
                }
            }

            override fun onUnavailable() {
                if (currentNetworkState != NetworkState.UNAVAILABLE) {
                    log(NetworkState.UNAVAILABLE)
                    super.onUnavailable()
                }
            }
        }
    } else null

    init {
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
            context.registerReceiver(networkBroadcastReceiver, IntentFilter().apply {
                addAction(CONNECTIVITY_ACTION)
            })
        }
    }

    fun register(onNetworkStateChangeListener: OnNetworkStateChangeListener) {
        this.onNetworkStateChangeListener = onNetworkStateChangeListener
    }

    fun register(onOnline: () -> Unit, onOffline: () -> Unit): NetworkCallback {
        onNetworkStateChangeListener = object : OnNetworkStateChangeListener {
            override fun onOnline() {
                onOnline.invoke()
                isOnline = true
            }

            override fun onOffline() {
                onOffline.invoke()
                isOnline = false
            }
        }
        return this
    }

    fun unregister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.unregisterNetworkCallback(networkCallback!!)
        } else {
            context.unregisterReceiver(networkBroadcastReceiver)
        }
    }

    interface OnNetworkStateChangeListener {
        fun onOnline()
        fun onOffline()
    }

    private fun log(networkState: NetworkState) {
        Log.d(javaClass.simpleName, networkState.name)
    }
}