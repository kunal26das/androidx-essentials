package androidx.essentials.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class Fragment(private val dataBinding: Boolean = false) : Fragment() {

    abstract val layout: Int
    abstract val viewModel: ViewModel
    protected lateinit var root: View
    protected open var binding: ViewDataBinding? = null
    inline fun <reified T : ViewModel> Fragment.sharedViewModel() = koinSharedViewModel<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log(Lifecycle.Event.ON_CREATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(javaClass.simpleName, Event.ON_CREATE_VIEW.name)
        root = when (dataBinding) {
            true -> {
                binding = DataBindingUtil.inflate(inflater, layout, container, false)
                binding?.lifecycleOwner = viewLifecycleOwner
                binding?.root!!
            }
            false -> inflater.inflate(layout, container, false)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(javaClass.simpleName, Event.ON_VIEW_CREATED.name)
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    override fun onStart() {
        log(Lifecycle.Event.ON_START)
        super.onStart()
    }

    override fun onResume() {
        log(Lifecycle.Event.ON_RESUME)
        super.onResume()
    }

    override fun onPause() {
        log(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        log(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroy() {
        log(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }

    open fun initObservers() {}

    private fun log(event: Lifecycle.Event) {
        Log.d(javaClass.simpleName, event.name)
    }

    companion object {

        private enum class Event {
            ON_CREATE_VIEW,
            ON_VIEW_CREATED
        }
    }

}