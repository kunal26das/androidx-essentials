package androidx.essentials.network.remote

import androidx.essentials.network.Builder
import retrofit2.Retrofit

open class RetrofitBuilder(
    private val builder: (Retrofit.Builder.() -> Unit)? = null
) : Builder<Retrofit>() {
    override fun initialize(): Retrofit {
        return Retrofit.Builder().apply {
            builder?.invoke(this)
        }.build()
    }
}