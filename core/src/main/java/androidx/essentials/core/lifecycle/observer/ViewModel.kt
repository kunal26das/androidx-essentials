package androidx.essentials.core.lifecycle.observer

import android.util.Log
import androidx.essentials.preferences.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel

abstract class ViewModel : ViewModel(), SharedPreferences {

    init {
        logViewModelLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onCleared() {
        logViewModelLifecycleEvent(Event.ON_CLEARED)
        super.onCleared()
    }

    private fun logViewModelLifecycleEvent(event: Enum<*>) {
        Log.d(javaClass.simpleName, event.name)
    }

    companion object {
        private enum class Event {
            ON_CLEARED
        }
    }

}