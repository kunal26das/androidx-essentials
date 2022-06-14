package androidx.essentials.playground

import android.app.Application
import androidx.essentials.network.Network
import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.network.NetworkRequest
import androidx.essentials.playground.network.Retrofit
import androidx.essentials.playground.repository.LibraryRepository
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class PlayGround : Application() {

    override fun onCreate() {
        super.onCreate()
        Resources.init(this)
        SharedPreferences.init(this)
        Stetho.initializeWithDefaults(this)
        Network.init(this, NetworkRequest.getInstance())
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(module {
                single { Retrofit.getInstance() }
                single { LibraryRepository(get(), get()) }
            })
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}