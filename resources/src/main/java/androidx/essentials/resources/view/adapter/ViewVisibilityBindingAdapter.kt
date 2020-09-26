package androidx.essentials.resources.view.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean?) {
    view.visibility = when (visible) {
        null -> View.INVISIBLE
        true -> View.VISIBLE
        false -> View.GONE
    }
}