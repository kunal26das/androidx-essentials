package androidx.essentials.resources

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun View.setVisible(isVisible: Boolean?) {
        this.isVisible = isVisible ?: false
    }

}

