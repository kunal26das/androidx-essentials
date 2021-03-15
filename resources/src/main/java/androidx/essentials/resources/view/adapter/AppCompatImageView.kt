package androidx.essentials.resources.view.adapter

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

object AppCompatImageView {

    @JvmStatic
    @BindingAdapter("srcCompat")
    fun AppCompatImageView.setDrawable(drawable: Drawable?) {
        this.setImageDrawable(drawable)
    }

}