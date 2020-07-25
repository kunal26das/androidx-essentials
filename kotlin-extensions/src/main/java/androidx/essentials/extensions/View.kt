package androidx.essentials.extensions

import android.view.View
import android.view.ViewTreeObserver

object View {
    fun View.onGlobalLayoutListener(action: (View) -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                action.invoke(this@onGlobalLayoutListener)
            }
        })
    }
}