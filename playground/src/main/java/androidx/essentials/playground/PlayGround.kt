package androidx.essentials.playground

import android.app.Application
import androidx.essentials.network.Network
import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.network.NetworkRequest
import com.facebook.stetho.Stetho
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlayGround : Application() {

    override fun onCreate() {
        super.onCreate()
        Resources.init(this)
        SharedPreferences.init(this)
        Stetho.initializeWithDefaults(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
        Network.init(this, NetworkRequest.getInstance())
    }

}