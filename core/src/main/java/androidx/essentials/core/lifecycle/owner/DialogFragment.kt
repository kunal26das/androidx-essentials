package androidx.essentials.core.lifecycle.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.R
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Events
import androidx.essentials.extensions.Coroutines.default
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.sharedViewModel as koinSharedViewModel

abstract class DialogFragment : AppCompatDialogFragment() {

    @PublishedApi
    internal val accessLayout
        get() = layout
    protected abstract val layout: Int

    @PublishedApi
    internal lateinit var container: ViewGroup
    val activity by lazy { context as Activity }
    val compositeDisposable = CompositeDisposable()
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()

    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewModel> BottomSheetDialogFragment.viewModel() =
        koinSharedViewModel<T>()

    inline fun <reified T : ViewDataBinding> DialogFragment.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout, container, false) as T
    }

    inline fun <reified T : Any> DialogFragment.subscribe(crossinline action: (T) -> Unit) =
        compositeDisposable.add(Events.subscribe<T> { action.invoke(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog)
    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        this.container = container?.default { binding }!!
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    protected open fun initObservers() = Unit

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) =
        observe(viewLifecycleOwner, { action.invoke(it) })

    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) =
        activity.apply { toast(resId, duration) }

    protected fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
        activity.apply { toast(s, duration) }

}