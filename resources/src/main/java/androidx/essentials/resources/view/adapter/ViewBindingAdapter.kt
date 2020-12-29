package androidx.essentials.resources.view.adapter

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun View.setVisible(isVisible: Boolean?) {
        visibility = when (isVisible) {
            null -> View.INVISIBLE
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

}

