package androidx.essentials.playground.network

import android.net.NetworkCapabilities
import androidx.essentials.network.NetworkRequestBuilder

class NetworkRequest : NetworkRequestBuilder({
    addTransportType(NetworkCapabilities.TRANSPORT_VPN)
    addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
    addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
})