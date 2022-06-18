package androidx.essentials.view

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.io.Serializable
import kotlin.Lazy

class Lazy<out T>(
    lifecycleOwner: LifecycleOwner?,
    private val initializer: () -> T
) : Lazy<T>, Serializable, LifecycleEventObserver {

    init {
        lifecycleOwner?.lifecycle?.addObserver(this)
    }

    @Volatile
    private var _value: Any? = UninitializedValue

    override val value: T
        @Suppress("UNCHECKED_CAST") get() {
            val _v1 = _value
            if (_v1 !== UninitializedValue) {
                return _v1 as T
            }
            return synchronized(this) {
                val _v2 = _value
                if (_v2 !== UninitializedValue) {
                    _v2 as T
                } else {
                    val typedValue = initializer()
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
            _value = UninitializedValue
        }
    }

}