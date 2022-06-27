package androidx.essentials.network

abstract class Builder<T> : () -> T {

    @Volatile
    private var instance: T? = null

    @Synchronized
    override fun invoke(): T {
        if (instance != null) return instance!!
        return initialize().also {
            instance = it
        }
    }

    protected abstract fun initialize(): T

}