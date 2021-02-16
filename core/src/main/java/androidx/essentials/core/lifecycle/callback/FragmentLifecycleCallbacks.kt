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
        logFragmentLifecycleEvent(fragment, Event.ON_ATTACH.name)
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_CREATE.name)
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_VIEW_CREATE.name)
    }

    override fun onFragmentStarted(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_START.name)
    }

    override fun onFragmentResumed(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_RESUME.name)
    }

    override fun onFragmentPaused(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_PAUSE.name)
    }

    override fun onFragmentStopped(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_STOP.name)
    }

    override fun onFragmentViewDestroyed(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_VIEW_DESTROY.name)
    }

    override fun onFragmentDestroyed(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Lifecycle.Event.ON_DESTROY.name)
    }

    override fun onFragmentDetached(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_DETACH.name)
    }

    override fun onFragmentSaveInstanceState(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        outState: Bundle
    ) {
        logFragmentLifecycleEvent(fragment, Event.ON_SAVE_INSTANCE_STATE.name)
    }

    private fun logFragmentLifecycleEvent(fragment: Fragment, event: String) {
        Log.d(fragment.javaClass.simpleName, event)
    }

    private enum class Event {
        ON_ATTACH,
        ON_DETACH,
        ON_SAVE_INSTANCE_STATE,
        ON_VIEW_CREATE,
        ON_VIEW_DESTROY
    }
}