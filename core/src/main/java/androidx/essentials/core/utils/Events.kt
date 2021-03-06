package androidx.essentials.core.utils

import androidx.essentials.core.lifecycle.observer.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

object Events : LifecycleObserver {

    val events: PublishSubject<Any> = PublishSubject.create()
    val compositeDisposable = mutableMapOf<LifecycleOwner, CompositeDisposable>()

    inline fun <reified T : Any> LifecycleOwner.subscribe(
        noinline action: ((data: T) -> Unit)? = null
    ) {
        if (!compositeDisposable.containsKey(this)) {
            compositeDisposable[this] = CompositeDisposable()
            this.addObserver()
        }
        compositeDisposable[this]?.add(
            events.ofType<T>().apply {
                subscribeOn(Schedulers.io())
            }.subscribe({
                action?.invoke(it)
            }, {
                it.printStackTrace()
            })
        )
    }

    fun LifecycleOwner.unsubscribe() = compositeDisposable[this]?.clear()

    fun Any.publish(event: Any) = events.onNext(event)

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        compositeDisposable[lifecycleOwner]?.clear()
        compositeDisposable.remove(lifecycleOwner)
        super.onDestroy(lifecycleOwner)
        lifecycleOwner.removeObserver()
    }

}