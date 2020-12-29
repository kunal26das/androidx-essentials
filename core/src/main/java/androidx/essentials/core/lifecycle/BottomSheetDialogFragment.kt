package androidx.essentials.core.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.R
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class BottomSheetDialogFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {

    /** View **/
    protected abstract val layout: Int
    protected lateinit var binding: T

    /** ViewModel **/
    protected open val viewModel = ViewModel()
    protected open val sharedViewModel = ViewModel()
    inline fun <reified T : ViewModel> BottomSheetDialogFragment.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewModel> BottomSheetDialogFragment.sharedViewModel() =
        koinSharedViewModel<T>()

    /** Toast **/
    private val toast by lazy { Toast.makeText(context, "", Toast.LENGTH_SHORT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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
        toast.apply {
            setDuration(duration)
            setText(resId)
        }.show()
    }

    protected fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        toast.apply {
            setDuration(duration)
            setText(s)
        }.show()
    }

}