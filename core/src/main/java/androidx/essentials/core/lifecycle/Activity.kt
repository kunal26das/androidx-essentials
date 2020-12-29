package androidx.essentials.core.lifecycle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity<T : ViewDataBinding> : AppCompatActivity() {

    /** View **/
    protected abstract val layout: Int
    protected lateinit var binding: T

    /** ViewModel **/
    protected open val viewModel = ViewModel()
    inline fun <reified T : ViewModel> AppCompatActivity.viewModel() = koinViewModel<T>()

    /** Fragment **/
    private val fragmentLifecycleCallbacks = FragmentLifecycleCallbacks()

    /** Toast **/
    private val toast by lazy { Toast.makeText(this, "", Toast.LENGTH_SHORT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
        initObservers()
    }

    open fun initObservers() = Unit

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, { action.invoke(it) })
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