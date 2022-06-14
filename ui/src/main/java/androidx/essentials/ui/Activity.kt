package androidx.essentials.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.extensions.Try
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class Activity : AppCompatActivity() {

    open val layout: Int? = null
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()
    inline fun <reified T : ViewDataBinding> Activity.dataBinding() = lazy {
        DataBindingUtil.setContentView(this, layout!!) as T
    }

    protected val contentFrameLayout by lazy {
        findViewById<ContentFrameLayout>(android.R.id.content)
    }

    @CallSuper
    @MainThread
    protected open fun onAttach(context: Context) = Unit

    final override fun onCreate(savedInstanceState: Bundle?) {
        onAttach(applicationContext)
        super.onCreate(savedInstanceState)
        when (binding) {
            null -> layout?.let {
                setContentView(it)
                onViewCreated(contentFrameLayout, savedInstanceState)
            }
            else -> binding?.let {
                it.lifecycleOwner = this
                onViewCreated(it.root, savedInstanceState)
            }
        }
        initObservers()
    }

    @CallSuper
    @MainThread
    protected open fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit

    @CallSuper
    @MainThread
    protected open fun initObservers() = Unit

    @CallSuper
    @MainThread
    open fun onDestroyView() {
        binding?.unbind()
    }

    override fun onDestroy() {
        onDestroyView()
        super.onDestroy()
        onDetach()
    }

    @CallSuper
    @MainThread
    protected open fun onDetach() = Unit

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity) { action.invoke(it) }
    }

    @Synchronized
    fun DialogFragment.show() {
        Try { if (!isAdded) showNow(supportFragmentManager, null) }
    }

}