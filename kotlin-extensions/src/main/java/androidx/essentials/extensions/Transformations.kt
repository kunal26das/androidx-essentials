package androidx.essentials.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Transformations {
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