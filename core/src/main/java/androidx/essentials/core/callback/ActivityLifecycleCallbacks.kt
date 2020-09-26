package androidx.essentials.core.callback

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.essentials.core.ui.Event
import androidx.lifecycle.Lifecycle

open class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        log(activity, Lifecycle.Event.ON_CREATE)
    }

    override fun onActivityStarted(activity: Activity) {
        log(activity, Lifecycle.Event.ON_START)
    }

    override fun onActivityResumed(activity: Activity) {
        log(activity, Lifecycle.Event.ON_RESUME)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        Log.d(activity.javaClass.simpleName, Event.ON_SAVE_INSTANCE_STATE.name)
    }

    override fun onActivityPaused(activity: Activity) {
        log(activity, Lifecycle.Event.ON_PAUSE)
    }

    override fun onActivityStopped(activity: Activity) {
        log(activity, Lifecycle.Event.ON_STOP)
    }

    override fun onActivityDestroyed(activity: Activity) {
        log(activity, Lifecycle.Event.ON_DESTROY)
    }

    private fun log(activity: Activity, event: Lifecycle.Event) {
        Log.d(activity.javaClass.simpleName, event.name)
    }

}