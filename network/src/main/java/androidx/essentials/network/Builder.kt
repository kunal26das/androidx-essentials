package androidx.essentials.network

@Suppress("PropertyName")
abstract class Builder<T> {

    @Volatile
    private var _value: Any? = UninitializedValue

    val INSTANCE: T
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
                    val typedValue = initialize()
                    _value = typedValue
                    typedValue
                }
            }
        }

    protected abstract fun initialize(): T

}