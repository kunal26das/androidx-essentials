package androidx.essentials.playground.repository

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.essentials.list.PagedList
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.home.library.LibraryView

class PagedLibraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : PagedList<MenuItem>(context, attrs, defStyleAttr) {

    override val viewHolder get() = LibraryView(context).viewHolder

    override fun onBindViewHolder(position: Int, item: MenuItem?, holder: ViewHolder) {
        if (holder is ListItemView.ViewHolder) holder.bind(item)
    }

}