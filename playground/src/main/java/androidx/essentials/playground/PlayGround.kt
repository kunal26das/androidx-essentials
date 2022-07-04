package androidx.essentials.playground

import android.app.Application
import android.os.StrictMode
import androidx.essentials.playground.network.ThreadPolicy
import androidx.essentials.playground.network.VmPolicy
import com.facebook.stetho.Stetho
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlayGround : Application() {

    override fun onCreate() {
        initStrictMode()
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun initStrictMode() {
        StrictMode.setThreadPolicy(
            ThreadPolicy(mainExecutor).INSTANCE
        )
        StrictMode.setVmPolicy(
            VmPolicy(mainExecutor).INSTANCE
        )
    }

}