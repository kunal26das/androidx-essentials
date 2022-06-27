package androidx.essentials.network

@Suppress("PropertyName")
abstract class Builder<T> {

    @Volatile
    private var value: T? = null

    val INSTANCE: T
        @Synchronized get() {
            if (value != null) return value!!
            value = initialize()
            return value!!
        }

    protected abstract fun initialize(): T

}