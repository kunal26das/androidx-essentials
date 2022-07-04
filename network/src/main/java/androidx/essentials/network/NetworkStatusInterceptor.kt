package androidx.essentials.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkStatusInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (IsNetworkAvailable.value == true)
            return chain.proceed(chain.request())
        else throw NetworkUnavailableException()
    }
}