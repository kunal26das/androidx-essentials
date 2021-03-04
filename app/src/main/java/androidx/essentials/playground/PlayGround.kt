package androidx.essentials.playground

import androidx.essentials.core.Application
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.ui.auto_complete.AutoCompleteViewModel
import androidx.essentials.playground.ui.chips.ChipsViewModel
import androidx.essentials.playground.ui.date.DateViewModel
import androidx.essentials.playground.ui.home.HomeViewModel
import androidx.essentials.playground.ui.location.LocationViewModel
import androidx.essentials.playground.ui.network.NetworkViewModel
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
        viewModel { NetworkViewModel() }
        viewModel { LocationViewModel() }
        viewModel { TextInputViewModel() }
        viewModel { AutoCompleteViewModel() }
        viewModel { SharedPreferencesViewModel() }
    }

    private val modules by lazy {
        single { NetworkCallback.getInstance(this) }
        single { LocationProvider.getInstance(this) }
    }

    override fun onCreate() {
        super.onCreate()
        setOf(viewModels, modules)
        Stetho.initializeWithDefaults(this)
    }

}