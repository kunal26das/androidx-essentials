package androidx.essentials.playground

import android.app.Application
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.essentials.network.SharedPreferences
import androidx.essentials.playground.network.Network
import androidx.essentials.playground.repository.LibraryRepository
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PlayGround : Application() {

    private val gson by lazy {
        GsonBuilder().create()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
            }
            retryOnConnectionFailure(true)
        }.build()
    }

    private val retrofit by lazy {
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()
    }

    override fun onCreate() {
        super.onCreate()
        Resources.init(this)
        SharedPreferences.init(this)
        Stetho.initializeWithDefaults(this)
        Network.init(this, NetworkRequest.Builder().apply {
            addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        }.build())
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(module {
                single { gson }
                single { retrofit }
                single { okHttpClient }
                single { LibraryRepository(get(), get()) }
            })
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    companion object {
        private const val BASE_URL = "https://play-ground-4b041.firebaseio.com"
    }

}