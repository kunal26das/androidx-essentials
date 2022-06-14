package androidx.essentials.view

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

class ViewBindingAdapter {
    companion object {

        @JvmStatic
        @BindingAdapter("visible")
        fun View.setVisible(isVisible: Boolean?) {
            this.isVisible = isVisible ?: false
        }

    }
}

