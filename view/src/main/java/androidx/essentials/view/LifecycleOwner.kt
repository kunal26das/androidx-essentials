package androidx.essentials.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

sealed interface LifecycleOwner {

    fun <T> LifecycleOwner.lazy(
        initializer: () -> T
    ) = LifecycleAwareLazy(
        when (this) {
            is ViewController -> lifecycleOwner
            is ComposeActivity -> this
            else -> null
        }, initializer
    )

    fun <T> LiveData<T>.observe(
        action: (T) -> Unit
    ): Observer<T> {
        val observer = Observer<T> { action.invoke(it) }
        observe(
            when (this@LifecycleOwner) {
                is ViewController -> lifecycleOwner
                is ComposeActivity -> this@LifecycleOwner
                else -> null
            }!!, observer
        )
        return observer
    }

}