package androidx.essentials.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.multidex.MultiDex
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

abstract class Application : Application(), Application.ActivityLifecycleCallbacks {

    lateinit var koinApplication: KoinApplication

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        Log.d(javaClass.simpleName, Lifecycle.Event.ON_CREATE.name)
        registerActivityLifecycleCallbacks(this)
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            koinApplication = this
            single { resources }
        }
    }

    inline fun <reified T> Application.single(noinline definition: () -> T) {
        koinApplication.modules(module { single { definition.invoke() } })
    }

    inline fun <reified T : ViewModel> Application.viewModel(noinline definition: () -> T) {
        koinApplication.modules(module { viewModel { definition.invoke() } })
    }

    override fun onTerminate() {
        Log.d(javaClass.simpleName, Event.ON_TERMINATE.name)
        unregisterActivityLifecycleCallbacks(this)
        super.onTerminate()
        stopKoin()
    }

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

    companion object {

        private enum class Event {
            ON_TERMINATE,
            ON_SAVE_INSTANCE_STATE
        }
    }
}