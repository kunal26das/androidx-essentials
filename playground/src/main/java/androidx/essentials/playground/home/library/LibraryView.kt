package androidx.essentials.playground.home.library

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.core.view.setMargins
import androidx.essentials.application.Resources.dp
import androidx.essentials.events.Events.publish
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : ListItemView(context, attrs, defStyleAttr) {

    override val layout = R.layout.item_library
    override val binding by dataBinding<ItemLibraryBinding>()

    init {
        radius = 4f.dp
        elevation = 4f.dp
        layoutParams?.setMargins(8.dp)
    }

    override fun bind(item: Any?): ItemLibraryBinding {
        binding.menuItem = item as? MenuItem
        return binding.apply {
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                publish(menuItem?.itemId!!)
            }
        }
    }

}