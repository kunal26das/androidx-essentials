package androidx.essentials.core.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel

abstract class ViewModel : ViewModel() {

    override fun onCleared() {
        log(Event.ON_CLEARED)
        super.onCleared()
    }

    private fun log(event: Event) {
        Log.d(javaClass.simpleName, event.name)
    }

    companion object {

        private enum class Event {
            ON_CLEARED
        }
    }

}