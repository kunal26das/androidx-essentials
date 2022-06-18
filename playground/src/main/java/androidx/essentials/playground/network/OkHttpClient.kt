package androidx.essentials.playground.network

import androidx.essentials.network.remote.OkHttpClientBuilder
import androidx.essentials.playground.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor

class OkHttpClient : OkHttpClientBuilder({
    if (BuildConfig.DEBUG) addNetworkInterceptor(StethoInterceptor())
    retryOnConnectionFailure(true)
})