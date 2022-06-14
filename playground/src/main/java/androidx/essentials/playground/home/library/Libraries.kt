package androidx.essentials.playground.home.library

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.essentials.playground.R
import androidx.essentials.view.List
import androidx.essentials.view.ListItemView

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : List<MenuItem>(context, attrs, defStyleAttr) {

    override val viewHolder get() = LibraryView(context).viewHolder

    override fun onBindViewHolder(position: Int, item: MenuItem?, holder: ViewHolder) {
        if (holder is ListItemView.ViewHolder) holder.bind(item)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("list")
        fun Libraries.submitList(
            libraries: kotlin.collections.List<MenuItem>?
        ) = submitList(libraries)

    }

}