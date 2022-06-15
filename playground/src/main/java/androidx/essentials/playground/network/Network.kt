package androidx.essentials.playground.network

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Network @Inject constructor(
    @ApplicationContext context: Context,
    networkRequest: NetworkRequest
) : NetworkCallback() {

    val isBlocked = MutableLiveData<Boolean>()
    val maxTimeToLive = MutableLiveData<Int>()
    val isAvailable = MutableLiveData<Boolean>()
    val linkProperties = MutableLiveData<LinkProperties>()
    val capabilities = MutableLiveData<NetworkCapabilities>()

    init {
        context.getSystemService(ConnectivityManager::class.java)
            .registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
        isAvailable.postValue(true)
        super.onAvailable(network)
    }

    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
        isBlocked.postValue(blocked)
    }

    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        capabilities.postValue(networkCapabilities)
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
        this.linkProperties.postValue(linkProperties)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        maxTimeToLive.postValue(maxMsToLive)
        super.onLosing(network, maxMsToLive)
    }

    override fun onLost(network: Network) {
        isAvailable.postValue(false)
        super.onLost(network)
    }

    override fun onUnavailable() {
        isAvailable.postValue(false)
        super.onUnavailable()
    }

}
