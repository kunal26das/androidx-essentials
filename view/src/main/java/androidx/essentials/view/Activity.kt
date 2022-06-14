package androidx.essentials.view

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

abstract class Activity : AppCompatActivity(), ViewController {

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

    fun DialogFragment.show(
        tag: String? = null
    ) = try {
        if (!isAdded) showNow(supportFragmentManager, tag)
        null
    } catch (e: Throwable) {
        e
    }

}