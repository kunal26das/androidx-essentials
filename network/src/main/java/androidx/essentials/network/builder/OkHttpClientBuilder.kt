package androidx.essentials.network.builder

import okhttp3.OkHttpClient

open class OkHttpClientBuilder(
    private val builder: (OkHttpClient.Builder.() -> Unit)? = null
) : Builder<OkHttpClient>() {
    override fun initialize(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            builder?.invoke(this)
        }.build()
    }
}