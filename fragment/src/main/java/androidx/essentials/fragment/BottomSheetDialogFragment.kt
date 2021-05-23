package androidx.essentials.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    @PublishedApi
    internal val accessLayout
        get() = layout
    protected open val layout: Int? = null

    @PublishedApi
    internal lateinit var container: ViewGroup
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()

    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }

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

}