package androidx.essentials.events

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

object Events : LifecycleObserver {

    val events: PublishSubject<Any> = PublishSubject.create()
    val compositeDisposable = mutableMapOf<LifecycleOwner, LinkedHashMap<Class<*>, Disposable>>()

    inline fun <reified T : Any> LifecycleOwner.subscribe(
        noinline action: ((data: T) -> Unit)? = null
    ) {
        if (!compositeDisposable.containsKey(this)) {
            compositeDisposable[this] = LinkedHashMap()
            lifecycle.addObserver(this@Events)
        }
        with(compositeDisposable[this]!!) {
            if (!containsKey(T::class.java)) {
                put(T::class.java, events.ofType<T>().apply {
                    subscribeOn(Schedulers.io())
                }.subscribe({
                    action?.invoke(it)
                }, {
                    it.printStackTrace()
                }))
            }
        }
    }

    inline fun <reified T : Any> LifecycleOwner.unsubscribe() {
        compositeDisposable[this]?.get(T::class.java)?.dispose()
    }

    fun Any.publish(event: Any) = events.onNext(event)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.removeObserver(this)
        compositeDisposable[lifecycleOwner]?.forEach {
            it.value.dispose()
        }
        compositeDisposable.remove(lifecycleOwner)
    }

}