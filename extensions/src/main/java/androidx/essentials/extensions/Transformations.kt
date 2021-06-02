package androidx.essentials.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

object Transformations {
    fun <Y> switchMap(
        vararg sources: LiveData<*>,
        switchMapFunction: (list: List<Any?>) -> LiveData<Y>
    ) = MediatorLiveData<Y>().apply {
        sources.forEach {
            addSource(it, object : Observer<Any?> {
                var mSource: LiveData<Y>? = null
                override fun onChanged(x: Any?) {
                    val newLiveData = switchMapFunction(sources.map { it.value })
                    if (mSource == newLiveData) return
                    if (mSource != null) {
                        removeSource(mSource!!)
                    }
                    mSource = newLiveData
                    if (mSource != null) {
                        addSource(mSource!!) {
                            value = it
                        }
                    }
                }
            })
        }
    }
}