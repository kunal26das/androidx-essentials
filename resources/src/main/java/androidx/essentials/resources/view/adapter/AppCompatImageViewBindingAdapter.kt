package androidx.essentials.resources.view.adapter

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

object AppCompatImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("srcCompat")
    fun AppCompatImageView.setImageDrawable(drawable: Drawable?) {
        this.setImageDrawable(drawable)
    }

}