package androidx.essentials.view

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData

sealed interface ViewController : LifecycleAwareLazy {

    val layout: Int
    val binding: ViewDataBinding

    private val inflater
        get() = LayoutInflater.from(getContext())

    val lifecycleOwner
        get() = when (this) {
            is Activity -> this
            is Fragment -> viewLifecycleOwner
            is DialogFragment -> viewLifecycleOwner
            is BottomSheetDialogFragment -> viewLifecycleOwner
            else -> null
        }

    private fun getContainer() = when (this) {
        is Activity -> null
        is Fragment -> container
        is DialogFragment -> container
        is BottomSheetDialogFragment -> container
        else -> null
    }

    private fun getContext() = when (this) {
        is Activity -> this
        is Fragment -> context
        is DialogFragment -> context
        is BottomSheetDialogFragment -> context
        else -> null
    }

    fun <T : ViewDataBinding> dataBinding() = lazy {
        when (this) {
            is Activity -> DataBindingUtil.setContentView<T>(this, layout)
            else -> DataBindingUtil.inflate(inflater, layout, getContainer(), false)
        }
    }

    fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(lifecycleOwner!!) { action.invoke(it) }
    }

}