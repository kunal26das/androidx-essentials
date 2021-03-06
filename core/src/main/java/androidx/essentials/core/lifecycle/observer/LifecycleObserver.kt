package androidx.essentials.core.lifecycle.observer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

interface LifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_START)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_RESUME)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_PAUSE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_DESTROY)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(lifecycleOwner: LifecycleOwner) {
        logLifecycleEvent(lifecycleOwner, Lifecycle.Event.ON_ANY)
    }

    private fun logLifecycleEvent(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(lifecycleOwner.javaClass.simpleName, event.name)
    }

    fun LifecycleOwner.addObserver() {
        lifecycle.addObserver(this@LifecycleObserver)
    }

    fun LifecycleOwner.removeObserver() {
        lifecycle.removeObserver(this@LifecycleObserver)
    }

}