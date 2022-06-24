package androidx.essentials.network

import android.os.StrictMode

open class VmPolicyBuilder(
    private val builder: StrictMode.VmPolicy.Builder.() -> Unit
) : Builder<StrictMode.VmPolicy>() {
    override fun initialize(): StrictMode.VmPolicy {
        return StrictMode.VmPolicy.Builder().apply {
            builder.invoke(this)
        }.build()
    }
}