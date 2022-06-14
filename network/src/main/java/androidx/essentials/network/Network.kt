package androidx.essentials.network

import android.content.Context
import android.net.ConnectivityManager.NetworkCallback
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.essentials.network.SharedPreferences.mutableLiveData
import androidx.lifecycle.MutableLiveData

object Network {

    val LINK_PROPERTIES = MutableLiveData<LinkProperties>()
    val CAPABILITIES = MutableLiveData<NetworkCapabilities>()

    val IS_BLOCKED by mutableLiveData<Boolean>(Preference.IS_NETWORK_BLOCKED)
    val MAX_TIME_TO_LIVE by mutableLiveData<Int>(Preference.MAX_TIME_TO_LIVE)
    val IS_AVAILABLE by mutableLiveData<Boolean>(Preference.IS_NETWORK_AVAILABLE)

    private val mNetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            IS_AVAILABLE.postValue(true)
            super.onAvailable(network)
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            super.onBlockedStatusChanged(network, blocked)
            IS_BLOCKED.postValue(blocked)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            CAPABILITIES.postValue(networkCapabilities)
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties)
            LINK_PROPERTIES.postValue(linkProperties)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            MAX_TIME_TO_LIVE.postValue(maxMsToLive)
            super.onLosing(network, maxMsToLive)
        }

        override fun onLost(network: Network) {
            IS_AVAILABLE.postValue(false)
            super.onLost(network)
        }

        override fun onUnavailable() {
            IS_AVAILABLE.postValue(false)
            super.onUnavailable()
        }
    }

    fun init(context: Context, networkRequest: NetworkRequest) {
        synchronized(this) {
            ConnectivityManager.getInstance(context)
                .registerNetworkCallback(networkRequest, mNetworkCallback)
        }
    }

    enum class Preference {
        IS_NETWORK_AVAILABLE,
        IS_NETWORK_BLOCKED,
        MAX_TIME_TO_LIVE,
    }

}
