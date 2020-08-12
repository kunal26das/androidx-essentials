package androidx.essentials.core.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.mvvm.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity(private val dataBinding: Boolean = false) : AppCompatActivity() {

    abstract val layout: Int
    abstract val viewModel: ViewModel
    protected lateinit var root: View
    protected open var binding: ViewDataBinding? = null
    inline fun <reified T : ViewModel> Activity.viewModel() = koinViewModel<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (dataBinding) {
            true -> {
                binding = DataBindingUtil.setContentView(this, layout)
                binding?.lifecycleOwner = this
            }
            false -> setContentView(layout)
        }
        root = when {
            dataBinding -> binding?.root!!
            else -> findViewById(android.R.id.content)
        }
        initObservers()
    }

    open fun initObservers() {}

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, Observer {
            action.invoke(it)
        })
    }

}