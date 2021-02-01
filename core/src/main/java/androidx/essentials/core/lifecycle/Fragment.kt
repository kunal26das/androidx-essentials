package androidx.essentials.core.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class Fragment : Fragment() {

    /** View **/
    protected abstract val layout: Int
    protected open lateinit var activity: Activity
    protected open val binding: ViewDataBinding? = null
    @PublishedApi
    internal val accessLayout
        get() = layout
    @PublishedApi
    internal lateinit var container: ViewGroup
    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewDataBinding> Fragment.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout, container, false) as T
    }

    /** ViewModel **/
    protected abstract val viewModel: ViewModel
    inline fun <reified T : ViewModel> Fragment.viewModel() = koinSharedViewModel<T>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        this.container = container!!
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

    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        activity.apply { toast(resId, duration) }
    }

    protected fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        activity.apply { toast(s, duration) }
    }

}