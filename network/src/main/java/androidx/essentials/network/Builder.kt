package androidx.essentials.network

abstract class Builder<T> {

    @Volatile
    private var instance: T? = null

    @Synchronized
    fun getInstance(): T {
        if (instance != null) return instance!!
        return initialize().also {
            instance = it
        }
    }

    protected abstract fun initialize(): T

}