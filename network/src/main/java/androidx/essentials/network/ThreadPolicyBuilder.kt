package androidx.essentials.network

import android.os.StrictMode

open class ThreadPolicyBuilder(
    private val builder: StrictMode.ThreadPolicy.Builder.() -> Unit
) : Builder<StrictMode.ThreadPolicy>() {
    override fun initialize(): StrictMode.ThreadPolicy {
        return StrictMode.ThreadPolicy.Builder().apply {
            builder.invoke(this)
        }.build()
    }
}