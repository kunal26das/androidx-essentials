package androidx.essentials.network.builder

import com.google.gson.Gson
import com.google.gson.GsonBuilder

open class GsonBuilder(
    private val builder: (GsonBuilder.() -> Unit)? = null
) : Builder<Gson>() {
    override fun initialize(): Gson {
        return GsonBuilder().apply {
            builder?.invoke(this)
        }.create()
    }
}