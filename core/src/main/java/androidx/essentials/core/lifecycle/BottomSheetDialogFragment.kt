package androidx.essentials.core.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.R
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    abstract val layout: Int
    abstract val viewModel: ViewModel
    lateinit var viewDataBinding: ViewDataBinding
    inline fun <reified T : ViewModel> BottomSheetDialogFragment.sharedViewModel() =
        koinSharedViewModel<T>()

    inline fun <reified T : ViewDataBinding> BottomSheetDialogFragment.dataBinding() =
        lazy { viewDataBinding as T }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            viewDataBinding = DataBindingUtil.inflate(inflater, layout, container, false)
            viewDataBinding.lifecycleOwner = viewLifecycleOwner
            viewDataBinding.root
        } catch (e: Exception) {
            inflater.inflate(layout, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    open fun initObservers() {}

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner, {
            action.invoke(it)
        })
    }
}