package androidx.essentials.playground

import androidx.essentials.core.Application
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.ui.firebase.FirebaseViewModel
import androidx.essentials.playground.ui.home.HomeViewModel
import androidx.essentials.playground.ui.io.InputOutputViewModel
import androidx.essentials.playground.ui.location.LocationViewModel
import androidx.essentials.playground.ui.network.NetworkViewModel
import androidx.essentials.playground.ui.shared_preferences.SharedPreferencesViewModel
import com.facebook.stetho.Stetho

class PlayGround : Application() {

    private val viewModels by lazy {
        viewModel { HomeViewModel() }
        viewModel { NetworkViewModel() }
        viewModel { FirebaseViewModel() }
        viewModel { LocationViewModel() }
        viewModel { InputOutputViewModel() }
        viewModel { SharedPreferencesViewModel() }
    }

    private val modules by lazy {
        single { androidx.essentials.preferences.SharedPreferences(this) }
        single { NetworkCallback.getInstance(this) }
        single { LocationProvider.getInstance(this) }
    }

    override fun onCreate() {
        super.onCreate()
        setOf(viewModels, modules)
        Stetho.initializeWithDefaults(this)
    }

}