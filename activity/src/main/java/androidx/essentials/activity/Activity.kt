package androidx.essentials.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.extensions.Context.getActivity
import androidx.essentials.extensions.TryCatchFinally.Try
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

abstract class Activity : AppCompatActivity() {

    @PublishedApi
    internal val accessLayout: Int?
        get() = layout
    protected open val layout: Int? = null
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()
    inline fun <reified T : ViewDataBinding> Activity.dataBinding() = lazy {
        DataBindingUtil.setContentView(this, accessLayout!!) as T
    }

    protected val contentFrameLayout: ContentFrameLayout by lazy {
        findViewById(android.R.id.content)
    }

    @CallSuper
    @MainThread
    protected open fun onAttach(context: Context) {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            FragmentLifecycleCallbackLogs,
            true
        )
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        onAttach(applicationContext)
        super.onCreate(savedInstanceState)
        when (binding) {
            null -> layout?.let {
                setContentView(it)
                onViewCreated(contentFrameLayout, savedInstanceState)
            }
            else -> binding?.apply {
                lifecycleOwner = this@Activity
                onViewCreated(root, savedInstanceState)
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
    protected open fun onDetach() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbackLogs)
    }

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, { action.invoke(it) })
    }

    @Synchronized
    fun DialogFragment.show() = Try { if (!isAdded) show(supportFragmentManager, null) }

    companion object {
        inline fun <reified T : android.app.Activity> Context.start(
            vararg extras: Pair<Enum<*>, Any>,
            flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
        ): android.app.Activity? {
            startActivity(Intent(this, T::class.java).apply {
                extras.forEach {
                    when (it.second) {
                        is Bundle -> putExtra(it.first.name, it.second as Bundle)
                        is Parcelable -> putExtra(it.first.name, it.second as Parcelable)
                        is Serializable -> putExtra(it.first.name, it.second as Serializable)
                        is Array<*> -> putExtra(it.first.name, it.second as Array<*>)
                        is Boolean -> putExtra(it.first.name, it.second as Boolean)
                        is BooleanArray -> putExtra(it.first.name, it.second as BooleanArray)
                        is Byte -> putExtra(it.first.name, it.second as Byte)
                        is ByteArray -> putExtra(it.first.name, it.second as ByteArray)
                        is Char -> putExtra(it.first.name, it.second as Char)
                        is CharArray -> putExtra(it.first.name, it.second as CharArray)
                        is CharSequence -> putExtra(it.first.name, it.second as CharSequence)
                        is Double -> putExtra(it.first.name, it.second as Double)
                        is DoubleArray -> putExtra(it.first.name, it.second as DoubleArray)
                        is Float -> putExtra(it.first.name, it.second as Float)
                        is FloatArray -> putExtra(it.first.name, it.second as FloatArray)
                        is Int -> putExtra(it.first.name, it.second as Int)
                        is IntArray -> putExtra(it.first.name, it.second as IntArray)
                        is Long -> putExtra(it.first.name, it.second as Long)
                        is LongArray -> putExtra(it.first.name, it.second as LongArray)
                        is Short -> putExtra(it.first.name, it.second as Short)
                        is ShortArray -> putExtra(it.first.name, it.second as ShortArray)
                        is String -> putExtra(it.first.name, it.second as String)
                    }
                }
                this.flags = flags
            })
            return getActivity()
        }
    }

}