package androidx.essentials.core.lifecycle.owner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.events.Events
import androidx.essentials.core.lifecycle.callback.FragmentLifecycleCallbacks
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity : AppCompatActivity() {

    abstract val layout: Int
    protected abstract val viewModel: ViewModel
    protected open val binding: ViewDataBinding? = null
    private val compositeDisposable = CompositeDisposable()
    private val toast by lazy { Toast.makeText(this, "", Toast.LENGTH_SHORT) }
    inline fun <reified T : ViewModel> Activity.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewDataBinding> Activity.dataBinding() = lazy {
        DataBindingUtil.setContentView(this, layout) as T
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks, true)
        onViewCreated(binding?.root!!, savedInstanceState)
        binding?.lifecycleOwner = this
        initObservers()
    }

    @CallSuper
    protected open fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit

    @CallSuper
    protected open fun initObservers() = Unit

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, { action.invoke(it) })
    }

    fun <T> Class<T>.subscribe(action: (T) -> Unit) {
        compositeDisposable.add(Events.subscribe(this) {
            action.invoke(it)
        })
    }

    protected fun resumeApplication() = if (
        !isTaskRoot and
        (intent.action != null) and
        (intent.action.equals(Intent.ACTION_MAIN)) and
        (intent.hasCategory(Intent.CATEGORY_LAUNCHER))
    ) {
        finish()
        true
    } else false

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks)
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        toast.apply {
            setDuration(duration)
            setText(resId)
        }.show()
    }

    fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        toast.apply {
            setDuration(duration)
            setText(s)
        }.show()
    }

}