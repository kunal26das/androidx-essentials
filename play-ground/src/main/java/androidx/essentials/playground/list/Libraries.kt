package androidx.essentials.playground.list

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.essentials.list.List
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.playground.view.LibraryView

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : List<MenuItem, ItemLibraryBinding>(context, attrs, defStyleAttr) {

    private var onItemClickListener: LibraryView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LibraryView(
        context = parent.context, attachToRoot = false
    ).viewHolder

    override fun onBindViewHolder(
        holder: ListItemView.ViewHolder<MenuItem, ItemLibraryBinding>,
        position: Int, item: MenuItem
    ) {
        holder.listItemView.binding.root.setOnClickListener {
            onItemClickListener?.onClick(item)
        }
    }

    fun setOnItemClickListener(onClick: (MenuItem) -> Unit) {
        onItemClickListener = object : LibraryView.OnItemClickListener {
            override fun onClick(item: MenuItem) {
                onClick(item)
            }
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("libraries")
        fun Libraries.submitLibraries(libraries: kotlin.collections.List<MenuItem>?) {
            submitList(libraries)
        }

    }

}