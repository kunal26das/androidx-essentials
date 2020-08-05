package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.essentials.list.List
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.recyclerview.widget.GridLayoutManager

class LibraryList(
    context: Context,
    attrs: AttributeSet? = null
) : List<MenuItem, ItemLibraryBinding>(context, attrs) {

    override val itemLayout = R.layout.item_library
    private var onItemClickListener: OnItemClickListener? = null
    override val mLayoutManager = GridLayoutManager(context, 2)

    override fun onBindViewHolder(itemView: ItemLibraryBinding, item: MenuItem) {
        itemView.menuItem = item
        itemView.executePendingBindings()
        itemView.root.setOnClickListener {
            onItemClickListener?.onClick(item)
        }
    }

    fun setOnItemClickListener(action: (MenuItem) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onClick(item: MenuItem) {
                action(item)
            }
        }
    }

    internal interface OnItemClickListener {
        fun onClick(item: MenuItem)
    }

}