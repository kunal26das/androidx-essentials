package androidx.essentials.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.multidex.MultiDex

open class Application : Application(), Application.ActivityLifecycleCallbacks {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Resources.init(this)
        registerActivityLifecycleCallbacks(this)
        logLifecycleEvent(javaClass.simpleName, LifecycleEvent.ON_CREATE)
    }

    @CallSuper
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_CREATE)
    }

    @CallSuper
    override fun onActivityStarted(activity: Activity) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_START)
    }

    @CallSuper
    override fun onActivityResumed(activity: Activity) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_RESUME)
    }

    @CallSuper
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_ANY)
    }

    @CallSuper
    override fun onActivityPaused(activity: Activity) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_PAUSE)
    }

    @CallSuper
    override fun onActivityStopped(activity: Activity) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_STOP)
    }

    @CallSuper
    override fun onActivityDestroyed(activity: Activity) {
        logLifecycleEvent(activity.javaClass.simpleName, Lifecycle.Event.ON_DESTROY)
    }

    override fun onTerminate() {
        logLifecycleEvent(javaClass.simpleName, LifecycleEvent.ON_TERMINATE)
        unregisterActivityLifecycleCallbacks(this)
        super.onTerminate()
    }

    private fun logLifecycleEvent(tag: String, event: Enum<*>) = Log.d(tag, event.name)

    companion object {
        private enum class LifecycleEvent {
            ON_CREATE, ON_TERMINATE
        }
    }
}