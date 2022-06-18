package androidx.essentials.view

sealed interface LifecycleAwareLazy {
    fun <T> LifecycleAwareLazy.lazy(initializer: () -> T): Lazy<T> {
        return when (this) {
            is ViewController -> Lazy(lifecycleOwner, initializer)
            is ComposeActivity -> Lazy(this, initializer)
        }
    }
}