package androidx.essentials.playground.list

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.essentials.playground.R
import androidx.essentials.playground.view.LibraryView

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : androidx.essentials.list.List<MenuItem, LibraryView>(context, attrs, defStyleAttr) {

    override val onCreateViewHolder
        get() = LibraryView(context).viewHolder

    companion object {

        @JvmStatic
        @BindingAdapter("libraries")
        fun Libraries.submitList(
            libraries: List<MenuItem>?
        ) = submitList(libraries)

    }

}