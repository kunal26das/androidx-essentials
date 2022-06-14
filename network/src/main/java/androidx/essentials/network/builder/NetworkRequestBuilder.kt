package androidx.essentials.network.builder

import android.net.NetworkRequest

open class NetworkRequestBuilder(
    private val builder: (NetworkRequest.Builder.() -> Unit)? = null
) : Builder<NetworkRequest>() {
    override fun initialize(): NetworkRequest {
        return NetworkRequest.Builder().apply {
            builder?.invoke(this)
        }.build()
    }
}