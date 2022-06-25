package androidx.essentials.view

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity

abstract class Activity : AppCompatActivity(), ViewController {

    protected fun <I, O> registerForActivityResult(
        contract: ActivityResultContract<I, O>,
    ) = registerForActivityResult(contract) {}

    @CallSuper
    @MainThread
    protected open fun onAttach(context: Context) = Unit

    final override fun onCreate(savedInstanceState: Bundle?) {
        onAttach(applicationContext)
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        onViewCreated(binding.root, savedInstanceState)
    }

    @CallSuper
    @MainThread
    protected open fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit

    @CallSuper
    @MainThread
    open fun onDestroyView() {
        binding.unbind()
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

}