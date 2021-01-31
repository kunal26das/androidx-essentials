package androidx.essentials.core.lifecycle.callback

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle

open class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        logActivityLifecycleEvent(activity, Lifecycle.Event.ON_CREATE)
    }

    override fun onActivityStarted(activity: Activity) {
        logActivityLifecycleEvent(activity, Lifecycle.Event.ON_START)
    }

    override fun onActivityResumed(activity: Activity) {
        logActivityLifecycleEvent(activity, Lifecycle.Event.ON_RESUME)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onActivityPaused(activity: Activity) {
        logActivityLifecycleEvent(activity, Lifecycle.Event.ON_PAUSE)
    }

    override fun onActivityStopped(activity: Activity) {
        logActivityLifecycleEvent(activity, Lifecycle.Event.ON_STOP)
    }

    override fun onActivityDestroyed(activity: Activity) {
        logActivityLifecycleEvent(activity, Lifecycle.Event.ON_DESTROY)
    }

    private fun logActivityLifecycleEvent(activity: Activity, event: Lifecycle.Event) {
        Log.d(activity.javaClass.simpleName, event.name)
    }

}