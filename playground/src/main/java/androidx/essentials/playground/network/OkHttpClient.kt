package androidx.essentials.playground.network

import androidx.essentials.network.NetworkStatusInterceptor
import androidx.essentials.network.OkHttpClientBuilder
import androidx.essentials.playground.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor

object OkHttpClient : OkHttpClientBuilder({
    if (BuildConfig.DEBUG) addNetworkInterceptor(StethoInterceptor())
    retryOnConnectionFailure(true)
    addInterceptor(NetworkStatusInterceptor())
})