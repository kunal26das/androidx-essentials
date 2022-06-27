package androidx.essentials.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

sealed interface LifecycleOwner {

    fun <T> LifecycleOwner.lazy(
        initializer: () -> T
    ) = LifecycleAwareLazy(
        when (this) {
            is ViewController -> lifecycleOwner
            is ComposeController -> lifecycleOwner
            else -> null
        }, initializer
    )

    fun <T> LiveData<T>.observe(
        observer: Observer<T>
    ) {
        observe(
            when (this@LifecycleOwner) {
                is ViewController -> lifecycleOwner
                is ComposeController -> lifecycleOwner
                else -> null
            }!!, observer
        )
    }

}