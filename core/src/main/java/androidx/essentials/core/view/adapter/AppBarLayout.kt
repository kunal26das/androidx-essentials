package androidx.essentials.core.view.adapter

import androidx.databinding.BindingAdapter
import androidx.essentials.core.view.AppBarLayout

object AppBarLayout {

    @JvmStatic
    @BindingAdapter("loading")
    fun AppBarLayout.setLoading(isLoading: Boolean?) {
        this.isLoading = isLoading ?: false
    }

}