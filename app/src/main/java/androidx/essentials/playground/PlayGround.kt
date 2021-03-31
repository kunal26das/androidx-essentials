package androidx.essentials.playground

import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.essentials.core.Application
import androidx.essentials.network.Network
import androidx.essentials.playground.ui.auto_complete.AutoCompleteViewModel
import androidx.essentials.playground.ui.chips.ChipsViewModel
import androidx.essentials.playground.ui.date.DateViewModel
import androidx.essentials.playground.ui.home.HomeViewModel
import androidx.essentials.playground.ui.preferences.SharedPreferencesViewModel
import androidx.essentials.playground.ui.text_input.TextInputViewModel
import androidx.essentials.playground.ui.time.TimeViewModel
import com.facebook.stetho.Stetho

class PlayGround : Application() {

    private val viewModels by lazy {
        viewModel { DateViewModel() }
        viewModel { HomeViewModel() }
        viewModel { TimeViewModel() }
        viewModel { ChipsViewModel() }
        viewModel { TextInputViewModel() }
        viewModel { AutoCompleteViewModel() }
        viewModel { SharedPreferencesViewModel() }
    }

    override fun onCreate() {
        super.onCreate()
        setOf(viewModels)
        Stetho.initializeWithDefaults(this)
        Network.init(this, NetworkRequest.Builder().apply {
            addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        }.build())
    }

}