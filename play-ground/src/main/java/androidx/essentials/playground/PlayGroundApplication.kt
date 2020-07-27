package androidx.essentials.playground

import androidx.essentials.core.Application
import androidx.essentials.events.Events
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback

class PlayGroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initViewModels()
        initComponents()
    }

    private fun initViewModels() {
        viewModel { PlayGroundViewModel() }
    }

    private fun initComponents() {
        single { NetworkCallback(applicationContext) }
        single { LocationProvider.getInstance(applicationContext) }
    }

    override fun onTerminate() {
        super.onTerminate()
        Events.clear()
    }

}