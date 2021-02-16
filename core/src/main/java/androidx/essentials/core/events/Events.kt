package androidx.essentials.core.events

import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

object Events {

    private val events = PublishSubject.create<Any>()

    internal fun <T> subscribe(
        eventClass: Class<T>,
        action: (data: T) -> Unit
    ) = events.ofType(eventClass)
        .subscribeOn(Schedulers.io())
        .subscribe({
            action(it)
        }, {
            it.printStackTrace()
        })

    fun publish(event: Any?) = events.onNext(event)

}