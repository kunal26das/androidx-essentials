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
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    /** View **/
    abstract val layout: Int
    lateinit var container: ViewGroup
    protected open val binding: ViewDataBinding? = null
    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewDataBinding> Fragment.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, layout, container, false) as T
    }

    /** ViewModel **/
    protected abstract val viewModel: ViewModel
    inline fun <reified T : ViewModel> BottomSheetDialogFragment.viewModel() =
        koinSharedViewModel<T>()

    /** Toast **/
    private val toast by lazy { Toast.makeText(context, "", Toast.LENGTH_SHORT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
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