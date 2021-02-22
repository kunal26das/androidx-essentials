package androidx.essentials.core

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.essentials.core.lifecycle.callback.ActivityLifecycleCallbacks
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.lifecycle.Lifecycle
import androidx.multidex.MultiDex
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class Application : Application() {

    lateinit var koinApplication: KoinApplication

    private val sharedPreferences: SharedPreferences by lazy {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !BuildConfig.DEBUG -> {
                EncryptedSharedPreferences.create(
                    this, packageName,
                    MasterKey.Builder(this).apply {
                        setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    }.build(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            }
            else -> getSharedPreferences(packageName, MODE_PRIVATE)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks)
        Log.d(javaClass.simpleName, Lifecycle.Event.ON_CREATE.name)
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            koinApplication = this
            single { resources }
            single { sharedPreferences }
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
        Log.d(javaClass.simpleName, Event.ON_TERMINATE.name)
        super.onTerminate()
        stopKoin()
    }

    private fun logApplicationLifecycleEvent(event: String) {
        Log.d(javaClass.simpleName, event)
    }

    companion object {

        private enum class Event {
            ON_TERMINATE
        }
    }
}