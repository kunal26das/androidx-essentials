package androidx.essentials.view

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.io.Serializable

class LifecycleAwareLazy<out T>(
    lifecycleOwner: LifecycleOwner? = null,
    private val initializer: (() -> T)? = null,
) : Lazy<T>, Serializable, LifecycleEventObserver {

    init {
        lifecycleOwner?.lifecycle?.addObserver(this)
    }

    @Volatile
    private var _value: Any? = UninitializedValue

    override val value: T
        @Suppress("UNCHECKED_CAST") get() {
            val v1 = _value
            if (v1 !== UninitializedValue) {
                return v1 as T
            }
            return synchronized(this) {
                val v2 = _value
                if (v2 !== UninitializedValue) {
                    v2 as T
                } else {
                    val typedValue = initializer?.invoke()!!
                    _value = typedValue
                    typedValue
                }
            }
        }

    override fun isInitialized(): Boolean {
        return _value !== UninitializedValue
    }

    override fun toString() = when {
        isInitialized() -> value.toString()
        else -> "Lazy value not initialized yet."
    }

    override fun onStateChanged(
        source: LifecycleOwner,
        event: Lifecycle.Event
    ) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            uninitialize()
        }
    }

    fun uninitialize() {
        _value = UninitializedValue
    }

}