package androidx.essentials.core

import androidx.databinding.BindingAdapter

@BindingAdapter("loading")
fun setLoading(appBarLayout: AppBarLayout, isLoading: Boolean) {
    appBarLayout.isLoading = isLoading
}