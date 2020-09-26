package androidx.essentials.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.mvvm.ViewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Fragment : Fragment() {

    abstract val layout: Int
    abstract val viewModel: ViewModel
    lateinit var viewDataBinding: ViewDataBinding
    inline fun <reified T : ViewModel> Fragment.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewModel> Fragment.sharedViewModel() = koinSharedViewModel<T>()
    inline fun <reified T : ViewDataBinding> Fragment.dataBinding() = lazy { viewDataBinding as T }

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