package androidx.essentials.core.utils

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

object Events {

    val events: PublishSubject<Any> = PublishSubject.create()

    inline fun <reified T : Any> subscribe(
        noinline action: ((data: T) -> Unit)? = null
    ): Disposable = events.ofType<T>().apply {
        subscribeOn(Schedulers.io())
    }.subscribe({
        action?.invoke(it)
    }, {
        it.printStackTrace()
    })

    fun publish(event: Any?) = events.onNext(event)

}