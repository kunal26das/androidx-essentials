package androidx.essentials.core.lifecycle.observer

import android.content.Context
import android.util.Log
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel

abstract class ViewModel : ViewModel() {

    protected val applicationContext by inject<Context>()

    init {
        logViewModelLifecycleEvent(Lifecycle.Event.ON_CREATE.name)
    }

    override fun onCleared() {
        logViewModelLifecycleEvent(Event.ON_CLEARED.name)
        super.onCleared()
    }

    private fun logViewModelLifecycleEvent(event: String) {
        if (javaClass.simpleName != ViewModel::class.java.simpleName) {
            Log.d(javaClass.simpleName, event)
        }
    }

    companion object {
        private enum class Event {
            ON_CLEARED
        }
    }

}