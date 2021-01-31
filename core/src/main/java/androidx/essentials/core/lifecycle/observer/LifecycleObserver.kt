package androidx.essentials.core.lifecycle.observer

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

object LifecycleObserver {

    private interface LifecycleObserver : androidx.lifecycle.LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            logLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            logLifecycleEvent(Lifecycle.Event.ON_START)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            logLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            logLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            logLifecycleEvent(Lifecycle.Event.ON_STOP)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            logLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny() {
            logLifecycleEvent(Lifecycle.Event.ON_ANY)
        }

        private fun logLifecycleEvent(event: Lifecycle.Event) {
            Log.d(javaClass.simpleName, event.name)
        }

    }

    fun androidx.lifecycle.LifecycleObserver.addObserver(context: Context) {
        if (context is LifecycleOwner) {
            with(context as LifecycleOwner) {
                lifecycle.addObserver(this@addObserver)
            }
        }
    }

    fun androidx.lifecycle.LifecycleObserver.removeObserver(context: Context) {
        if (context is LifecycleOwner) {
            with(context as LifecycleOwner) {
                lifecycle.removeObserver(this@removeObserver)
            }
        }
    }

}