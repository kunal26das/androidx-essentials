package androidx.essentials.core.view.adapter

import androidx.databinding.BindingAdapter
import androidx.essentials.core.view.AppBarLayout

object AppBarLayoutBindingAdapter {

    @JvmStatic
    @BindingAdapter("loading")
    fun setLoading(appBarLayout: AppBarLayout, isLoading: Boolean) {
        appBarLayout.isLoading = isLoading
    }

}