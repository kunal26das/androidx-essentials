package androidx.essentials.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

class Context {
    companion object {

        private inline fun <reified T : Activity> Context.getActivity(): T? {
            var context = this
            while (context is ContextWrapper) {
                if (context is T) return context
                context = context.baseContext
            }
            return null
        }

        val Context.appCompatActivity get() = getActivity<AppCompatActivity>()

    }
}