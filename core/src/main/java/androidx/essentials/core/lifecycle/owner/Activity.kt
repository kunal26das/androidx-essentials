package androidx.essentials.core.lifecycle.owner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.lifecycle.callback.FragmentLifecycleCallbacks
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Events
import androidx.essentials.extensions.TryCatch.Try
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity : AppCompatActivity() {

    abstract val layout: Int
    val compositeDisposable = CompositeDisposable()
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()
    private val toast by lazy { Toast.makeText(this, "", Toast.LENGTH_SHORT) }
    inline fun <reified T : ViewModel> Activity.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewDataBinding> Activity.dataBinding() = lazy {
        DataBindingUtil.setContentView(this, layout) as T
    }

    inline fun <reified T : Any> Activity.subscribe(crossinline action: (T) -> Unit) =
        compositeDisposable.add(Events.subscribe<T> { action.invoke(it) })

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

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, { action.invoke(it) })
    }

    fun DialogFragment.show() = Try { if (!isAdded) show(supportFragmentManager, null) }

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

    override fun onBackPressed() {
        if (!onSupportNavigateUp()) {
            super.onBackPressed()
        }
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

    companion object {
        inline fun <reified T : Activity> Context.start(
            bundle: Bundle = Bundle.EMPTY,
            flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
        ): Activity? {
            startActivity(Intent(this, T::class.java).apply {
                this.flags = flags
                putExtras(bundle)
            })
            return this as? Activity
        }
    }

}