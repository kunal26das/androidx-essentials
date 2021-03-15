package androidx.essentials.core.lifecycle.callback

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

internal object FragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_ATTACH)
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_CREATE)
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_VIEW_CREATE)
    }

    override fun onFragmentStarted(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_START)
    }

    override fun onFragmentResumed(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_RESUME)
    }

    override fun onFragmentPaused(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_PAUSE)
    }

    override fun onFragmentStopped(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_STOP)
    }

    override fun onFragmentViewDestroyed(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_VIEW_DESTROY)
    }

    override fun onFragmentDestroyed(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_DESTROY)
    }

    override fun onFragmentDetached(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_DETACH)
    }

    override fun onFragmentSaveInstanceState(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        outState: Bundle
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_SAVE_INSTANCE_STATE)
    }

    private fun logFragmentLifecycleEvent(fragment: Fragment, event: Enum<*>) {
        Log.d(fragment.javaClass.simpleName, event.name)
    }

    private enum class Event {
        ON_ATTACH,
        ON_DETACH,
        ON_SAVE_INSTANCE_STATE,
        ON_VIEW_CREATE,
        ON_VIEW_DESTROY
    }
}