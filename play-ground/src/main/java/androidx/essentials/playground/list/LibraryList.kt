package androidx.essentials.playground.list

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.ViewGroup
import androidx.essentials.list.List
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.playground.view.LibraryView

class LibraryList(
    context: Context,
    attrs: AttributeSet? = null
) : List<MenuItem, ItemLibraryBinding>(context, attrs) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup) = LibraryView(
        attachToRoot = false,
        context = parent.context,
        listOrientation = orientation
    ).viewHolder

    override fun onBindViewHolder(holder: ListItemView.ViewHolder<MenuItem, ItemLibraryBinding>) {
        holder.listItemView.binding.root.setOnClickListener {
            holder.listItemView.item?.apply {
                onItemClickListener?.onClick(this)
            }
        }
    }

    fun setOnItemClickListener(action: (MenuItem) -> Unit) {
        onItemClickListener = object :
            OnItemClickListener {
            override fun onClick(item: MenuItem) {
                action(item)
            }
        }
    }

    internal interface OnItemClickListener {
        fun onClick(item: MenuItem)
    }

}