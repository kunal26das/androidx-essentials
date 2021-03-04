package androidx.essentials.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object Context {

    inline fun <reified T : Activity> Context.getActivity(): T? {
        var context = this
        while (context is ContextWrapper) {
            if (context is T) return context
            context = context.baseContext
        }
        return null
    }

}