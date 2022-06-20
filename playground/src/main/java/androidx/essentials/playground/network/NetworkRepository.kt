package androidx.essentials.playground.network

import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkRequest
import androidx.essentials.network.local.SharedPreferences
import androidx.lifecycle.MutableLiveData
import java.io.Closeable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    networkRequest: NetworkRequest,
    sharedPreferences: SharedPreferences,
) : NetworkCallback(), Closeable {

    val networkLinkProperties = MutableLiveData<LinkProperties>()
    val networkCapabilities = MutableLiveData<NetworkCapabilities>()

    val maxTimeToLive by sharedPreferences.mutableLiveData<Int>(KEY_MAX_TIME_TO_LIVE)
    val isNetworkBlocked by sharedPreferences.mutableLiveData<Boolean>(KEY_IS_NETWORK_BLOCKED)
    val isNetworkAvailable by sharedPreferences.mutableLiveData<Boolean>(KEY_IS_NETWORK_AVAILABLE)

    init {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.postValue(true)
        super.onAvailable(network)
    }

    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
        isNetworkBlocked.postValue(blocked)
    }

    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        this.networkCapabilities.postValue(networkCapabilities)
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
        this.networkLinkProperties.postValue(linkProperties)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        maxTimeToLive.postValue(maxMsToLive)
        super.onLosing(network, maxMsToLive)
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.postValue(false)
        super.onLost(network)
    }

    override fun onUnavailable() {
        isNetworkAvailable.postValue(false)
        super.onUnavailable()
    }

    override fun close() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    companion object {
        private const val KEY_MAX_TIME_TO_LIVE = "max_time_to_live"
        private const val KEY_IS_NETWORK_BLOCKED = "is_network_blocked"
        private const val KEY_IS_NETWORK_AVAILABLE = "is_network_available"
    }

}
