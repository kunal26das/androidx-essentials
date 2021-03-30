package androidx.essentials.core

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.essentials.core.lifecycle.callback.ActivityLifecycleCallbacks
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.preferences.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.multidex.MultiDex
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

open class Application : Application() {

    lateinit var koinApplication: KoinApplication

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks)
        logApplicationLifecycleEvent(Lifecycle.Event.ON_CREATE)
        super.onCreate()
        startKoin {
            SharedPreferences.init(applicationContext, SharedPreferences.Mode.ENCRYPTED)
            androidContext(applicationContext)
            koinApplication = this
            single { resources }
        }
    }

    inline fun <reified T> Application.module(modules: Module) {
        koinApplication.modules(modules)
    }

    inline fun <reified T> Application.modules(modules: List<Module>) {
        koinApplication.modules(modules)
    }

    inline fun <reified T> Application.single(noinline definition: () -> T) {
        koinApplication.modules(module { single { definition.invoke() } })
    }

    inline fun <reified T : ViewModel> Application.viewModel(noinline definition: () -> T) {
        koinApplication.modules(module { viewModel { definition.invoke() } })
    }

    override fun onTerminate() {
        unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks)
        logApplicationLifecycleEvent(Event.ON_TERMINATE)
        super.onTerminate()
        stopKoin()
    }

    private fun logApplicationLifecycleEvent(event: Enum<*>) {
        Log.d(javaClass.simpleName, event.name)
    }

    companion object {
        private enum class Event {
            ON_TERMINATE
        }
    }
}