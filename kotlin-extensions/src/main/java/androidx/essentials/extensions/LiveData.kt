package androidx.essentials.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LiveData {
    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    fun <Y> switchMap(
        vararg sources: LiveData<*>,
        switchMapFunction: (list: List<Any?>) -> LiveData<Y>
    ): LiveData<Y?>? {
        val result = MediatorLiveData<Y?>()
        CoroutineScope(Dispatchers.Default).launch {
            sources.forEach {
                CoroutineScope(Dispatchers.Main).launch {
                    result.addSource(it, object : Observer<Any?> {
                        var mSource: LiveData<Y>? = null
                        override fun onChanged(x: Any?) {
                            val newLiveData = switchMapFunction(sources.map { it.value })
                            if (mSource === newLiveData) {
                                return
                            }
                            if (mSource != null) {
                                result.removeSource(mSource!!)
                            }
                            mSource = newLiveData
                            if (mSource != null) {
                                result.addSource(
                                    mSource!!
                                ) { y -> result.value = y }
                            }
                        }
                    })
                }
            }
        }
        return result
    }
}