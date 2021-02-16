package androidx.essentials.playground.list

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.view.updateMargins
import androidx.databinding.BindingAdapter
import androidx.essentials.core.events.Events
import androidx.essentials.core.utils.Resources.dp
import androidx.essentials.list.List
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.playground.ui.PlayGroundActivity
import androidx.essentials.playground.view.LibraryView

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : List<MenuItem, ItemLibraryBinding>(context, attrs, defStyleAttr) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LibraryView(
        context = context, attachToRoot = false
    ).apply {
        (layoutParams as MarginLayoutParams).updateMargins(8.dp, 8.dp, 8.dp, 8.dp)
    }.viewHolder

    override fun onBindViewHolder(
        holder: ListItemView.ViewHolder<MenuItem, ItemLibraryBinding>,
        position: Int, item: MenuItem
    ) {
        holder.listItemView.binding.root.setOnClickListener {
            Events.publish(PlayGroundActivity.Destination.getById(item.itemId))
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("libraries")
        fun Libraries.submitList(libraries: kotlin.collections.List<MenuItem>?) {
            submitList(libraries)
        }

    }

}