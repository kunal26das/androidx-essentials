package androidx.essentials.playground

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlayGround : Application() {

    override fun onCreate() {
        initStrictMode()
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private fun initStrictMode() {
        StrictMode.setThreadPolicy(
            ThreadPolicy(mainExecutor).getInstance()
        )
        StrictMode.setVmPolicy(
            VmPolicy(mainExecutor).getInstance()
        )
    }

}