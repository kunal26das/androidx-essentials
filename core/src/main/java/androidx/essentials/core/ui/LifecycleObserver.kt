package androidx.essentials.core.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

object LifecycleObserver {

    interface LifecycleObserver : androidx.lifecycle.LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            log(Lifecycle.Event.ON_CREATE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            log(Lifecycle.Event.ON_START)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            log(Lifecycle.Event.ON_RESUME)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            log(Lifecycle.Event.ON_PAUSE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            log(Lifecycle.Event.ON_STOP)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            log(Lifecycle.Event.ON_DESTROY)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny() {
            log(Lifecycle.Event.ON_ANY)
        }

        private fun log(event: Lifecycle.Event) {
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