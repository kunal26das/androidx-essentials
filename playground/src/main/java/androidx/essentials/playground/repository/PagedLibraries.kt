package androidx.essentials.playground.repository

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.essentials.playground.R
import androidx.essentials.playground.home.library.LibraryView
import androidx.essentials.view.PagedList

class PagedLibraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : PagedList<MenuItem>(context, attrs, defStyleAttr) {

    override val viewHolder get() = LibraryView(context).viewHolder

    override fun onBindViewHolder(position: Int, item: MenuItem?, holder: ViewHolder) {
        if (holder is androidx.essentials.view.ViewHolder) holder.bind(item)
    }

}