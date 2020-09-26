package androidx.essentials.core

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.essentials.core.callback.ActivityLifecycleCallbacks
import androidx.essentials.core.mvvm.ViewModel
import androidx.essentials.core.ui.Event
import androidx.lifecycle.Lifecycle
import androidx.multidex.MultiDex
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

abstract class Application : Application() {

    lateinit var koinApplication: KoinApplication
    private val activityLifecycleCallbacks = ActivityLifecycleCallbacks()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        Log.d(javaClass.simpleName, Lifecycle.Event.ON_CREATE.name)
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
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        Log.d(javaClass.simpleName, Event.ON_TERMINATE.name)
        super.onTerminate()
        stopKoin()
    }


}