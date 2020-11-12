package androidx.essentials.core.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Fragment<T : ViewDataBinding> : Fragment() {

    /** View **/
    protected abstract val layout: Int
    protected lateinit var binding: T

    /** ViewModel **/
    protected open val viewModel = ViewModel()
    protected open val sharedViewModel = ViewModel()
    inline fun <reified T : ViewModel> Fragment.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewModel> Fragment.sharedViewModel() = koinSharedViewModel<T>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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