package androidx.essentials.playground

import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.essentials.core.Application
import androidx.essentials.network.Network
import androidx.essentials.preferences.SharedPreferences
import com.facebook.stetho.Stetho

class PlayGround : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        SharedPreferences.init(this, SharedPreferences.Mode.PLAINTEXT)
        Network.init(this, NetworkRequest.Builder().apply {
            addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        }.build())
    }

}