package androidx.essentials.core.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.mvvm.ViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity(private val dataBinding: Boolean = false) : AppCompatActivity() {

    abstract val layout: Int
    abstract val viewModel: ViewModel
    protected lateinit var root: View
    protected open var binding: ViewDataBinding? = null
    inline fun <reified T : ViewModel> Activity.viewModel() = koinViewModel<T>()

    private val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentAttached(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            context: Context
        ) {
            Log.d(fragment.javaClass.simpleName, Event.ON_ATTACH.name)
        }

        override fun onFragmentCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            savedInstanceState: Bundle?
        ) {
            log(fragment, Lifecycle.Event.ON_CREATE)
        }

        override fun onFragmentViewCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            Log.d(fragment.javaClass.simpleName, Event.ON_VIEW_CREATE.name)
        }

        override fun onFragmentStarted(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            log(fragment, Lifecycle.Event.ON_START)
        }

        override fun onFragmentResumed(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            log(fragment, Lifecycle.Event.ON_RESUME)
        }

        override fun onFragmentPaused(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            log(fragment, Lifecycle.Event.ON_PAUSE)
        }

        override fun onFragmentStopped(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            log(fragment, Lifecycle.Event.ON_STOP)
        }

        override fun onFragmentDestroyed(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            log(fragment, Lifecycle.Event.ON_DESTROY)
        }

        override fun onFragmentViewDestroyed(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            Log.d(fragment.javaClass.simpleName, Event.ON_VIEW_DESTROY.name)
        }

        override fun onFragmentDetached(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            Log.d(fragment.javaClass.simpleName, Event.ON_DETACH.name)
        }

        override fun onFragmentSaveInstanceState(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            outState: Bundle
        ) {
            Log.d(fragment.javaClass.simpleName, Event.ON_SAVE_INSTANCE_STATE.name)
        }

        private fun log(fragment: Fragment, event: Lifecycle.Event) {
            Log.d(fragment.javaClass.simpleName, event.name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (dataBinding) {
            true -> {
                binding = DataBindingUtil.setContentView(this, layout)
                binding?.lifecycleOwner = this
            }
            false -> setContentView(layout)
        }
        root = when {
            dataBinding -> binding?.root!!
            else -> findViewById(android.R.id.content)
        }
        initObservers()
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
    }

    open fun initObservers() {}

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, {
            action.invoke(it)
        })
    }

    protected fun resumeApplication(): Boolean {
        if (!isTaskRoot
            && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
            && intent.action != null
            && intent.action.equals(Intent.ACTION_MAIN)
        ) {
            finish()
            return true
        }
        return false
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
        super.onDestroy()
    }
}