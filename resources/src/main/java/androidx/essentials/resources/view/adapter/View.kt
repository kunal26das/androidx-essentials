package androidx.essentials.resources.view.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object View {

    @JvmStatic
    @BindingAdapter("visible")
    fun View.setVisible(isVisible: Boolean?) {
        this.isVisible = isVisible ?: false
    }

}

