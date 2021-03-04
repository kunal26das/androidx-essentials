package androidx.essentials.playground.ui.home.library

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.essentials.playground.R

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : androidx.essentials.list.List<MenuItem, LibraryView>(context, attrs, defStyleAttr) {

    override val onCreateViewHolder
        get() = LibraryView(context).viewHolder

    companion object {

        @JvmStatic
        @BindingAdapter("list")
        fun Libraries.submitList(
            libraries: List<MenuItem>?
        ) = submitList(libraries)

    }

}