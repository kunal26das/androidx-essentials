package androidx.essentials.network

abstract class Builder<T>(
    private val builder: () -> T
) {

    private var instance: T? = null

    @Synchronized
    fun getInstance(): T {
        if (instance != null) return instance!!
        return builder.invoke().also {
            instance = it
        }
    }

}