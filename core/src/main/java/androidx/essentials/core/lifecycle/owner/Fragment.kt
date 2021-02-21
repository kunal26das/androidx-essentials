package androidx.essentials.core.lifecycle.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.extensions.Coroutines.default
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class Fragment : Fragment() {

    @PublishedApi
    internal val accessLayout
        get() = layout
    protected abstract val layout: Int

    @PublishedApi
    internal lateinit var container: ViewGroup
    protected open val binding: ViewDataBinding? = null
    private val activity by lazy { context as Activity }
    protected open val viewModel by viewModels<ViewModel>()

    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewModel> Fragment.viewModel() = koinSharedViewModel<T>()
    inline fun <reified T : ViewDataBinding> Fragment.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout, container, false) as T
    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        this.container = container?.default { binding }!!
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    open fun initObservers() = Unit

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner, { action.invoke(it) })
    }

    protected fun <T> Class<T>.subscribe(action: (T) -> Unit) = activity.apply {
        subscribe(action)
    }

    protected fun DialogFragment.show() = this@Fragment.activity.apply { show() }

    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = activity.apply {
        toast(resId, duration)
    }

    protected fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) = activity.apply {
        toast(s, duration)
    }

}