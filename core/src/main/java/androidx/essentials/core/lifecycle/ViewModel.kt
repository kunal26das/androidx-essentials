package androidx.essentials.core.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel

abstract class ViewModel : ViewModel() {

    init {
        logViewModelLifecycleEvent(Lifecycle.State.CREATED.name)
    }

    override fun onCleared() {
        logViewModelLifecycleEvent(Event.ON_CLEARED.name)
        super.onCleared()
    }

    private fun logViewModelLifecycleEvent(event: String) {
        Log.d(javaClass.simpleName, event)
    }

    companion object {

        private enum class Event {
            ON_CLEARED
        }
    }

}