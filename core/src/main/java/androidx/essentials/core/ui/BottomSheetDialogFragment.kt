package androidx.essentials.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.R
import androidx.essentials.core.mvvm.ViewModel
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class BottomSheetDialogFragment(private val dataBinding: Boolean = false) :
    BottomSheetDialogFragment() {

    abstract val layout: Int
    abstract val viewModel: ViewModel
    protected lateinit var root: View
    protected var binding: ViewDataBinding? = null
    inline fun <reified T : ViewModel> Fragment.sharedViewModel() = koinSharedViewModel<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    open fun initObservers() {}

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer {
            action.invoke(it)
        })
    }
}