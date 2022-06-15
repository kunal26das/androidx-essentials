package androidx.essentials.network

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import androidx.essentials.network.local.SharedPreferences
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Singleton
class Network(
    @ApplicationContext context: Context,
    sharedPreferences: SharedPreferences,
    networkRequest: NetworkRequest
) {

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

    init {
        context.getSystemService(ConnectivityManager::class.java)
            .registerNetworkCallback(networkRequest, mNetworkCallback)
    }

    val LINK_PROPERTIES = MutableLiveData<LinkProperties>()
    val CAPABILITIES = MutableLiveData<NetworkCapabilities>()

    val IS_BLOCKED by sharedPreferences.mutableLiveData<Boolean>(Preference.is_network_blocked)
    val MAX_TIME_TO_LIVE by sharedPreferences.mutableLiveData<Int>(Preference.max_time_to_live)
    val IS_AVAILABLE by sharedPreferences.mutableLiveData<Boolean>(Preference.is_network_available)


    @Suppress("EnumEntryName")
    enum class Preference {
        is_network_available,
        is_network_blocked,
        max_time_to_live,
    }

}
