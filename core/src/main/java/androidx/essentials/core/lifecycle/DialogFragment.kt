package androidx.essentials.core.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.R
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class DialogFragment : AppCompatDialogFragment() {

    protected abstract val layout: Int
    protected abstract val viewModel: ViewModel
    protected open val binding: ViewDataBinding? = null
    protected val activity by lazy { context as Activity }

    @PublishedApi
    internal val accessLayout
        get() = layout
    @PublishedApi
    internal lateinit var container: ViewGroup
    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewDataBinding> BottomSheetDialogFragment.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout, container, false) as T
    }

    inline fun <reified T : ViewModel> BottomSheetDialogFragment.viewModel() =
        koinSharedViewModel<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        this.container = container!!
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root ?: container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    protected open fun initObservers() = Unit

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner, { action.invoke(it) })
    }

    protected fun <T> Class<T>.subscribe(action: (T) -> Unit) {
        activity.apply { subscribe(action) }
    }

    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        activity.apply { toast(resId, duration) }
    }

    protected fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        activity.apply { toast(s, duration) }
    }

}