package androidx.essentials.network

@Suppress("PropertyName")
abstract class Builder<T> {

    @Volatile
    private var value: Any? = UninitializedValue

    val INSTANCE: T
        @Suppress("UNCHECKED_CAST") get() {
            val v1 = value
            if (v1 !== UninitializedValue) {
                return v1 as T
            }
            return synchronized(this) {
                val v2 = value
                if (v2 !== UninitializedValue) {
                    v2 as T
                } else {
                    val typedValue = initialize()
                    value = typedValue
                    typedValue
                }
            }
        }

    protected abstract fun initialize(): T

}