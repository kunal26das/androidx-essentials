package androidx.essentials.core.lifecycle.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.R
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    @PublishedApi
    internal val accessLayout
        get() = layout
    protected open val layout: Int? = null

    @PublishedApi
    internal lateinit var container: ViewGroup
    val activity by lazy { context as Activity }
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()

    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewModel> BottomSheetDialogFragment.viewModel() =
        koinSharedViewModel<T>()

    inline fun <reified T : ViewDataBinding> BottomSheetDialogFragment.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout!!, container, false) as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        this.container = container!!
        return when (binding) {
            null -> layout?.let {
                inflater.inflate(it, container, false)
            }
            else -> binding?.apply {
                lifecycleOwner = viewLifecycleOwner
            }?.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    protected open fun initObservers() = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) =
        observe(viewLifecycleOwner, { action.invoke(it) })

    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) =
        activity.apply { toast(resId, duration) }

    protected fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
        activity.apply { toast(s, duration) }

}