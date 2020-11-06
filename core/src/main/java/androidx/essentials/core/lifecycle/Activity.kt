package androidx.essentials.core.lifecycle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity : AppCompatActivity() {

    protected abstract val layout: Int
    protected open val viewModel = ViewModel()
    lateinit var viewDataBinding: ViewDataBinding
    private val fragmentLifecycleCallbacks = FragmentLifecycleCallbacks()

    inline fun <reified T : ViewModel> Activity.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewDataBinding> Activity.dataBinding() = lazy { viewDataBinding as T }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            viewDataBinding = DataBindingUtil.setContentView(this, layout)
            viewDataBinding.lifecycleOwner = this
        } catch (e: Exception) {
            setContentView(layout)
        }
        initObservers()
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
    }

    open fun initObservers() {}

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, {
            action.invoke(it)
        })
    }

    protected fun resumeApplication(): Boolean {
        return if (!isTaskRoot
            && intent.action != null
            && intent.action.equals(Intent.ACTION_MAIN)
            && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
        ) {
            finish()
            true
        } else false
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
        super.onDestroy()
    }
}