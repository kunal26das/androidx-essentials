package androidx.essentials.playground.ui.home.library

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.core.view.setMargins
import androidx.essentials.core.utils.Events
import androidx.essentials.core.utils.Resources.dp
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.playground.ui.home.HomeActivity

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : ListItemView<MenuItem>(context, attrs, defStyleAttr) {

    override val layout = R.layout.item_library
    override val binding by dataBinding<ItemLibraryBinding>()

    init {
        radius = 8f.dp
        elevation = 4f.dp
        layoutParams?.setMargins(8.dp)
    }

    override fun bind(item: MenuItem) = binding.apply {
        menuItem = item
        executePendingBindings()
        root.setOnClickListener {
            HomeActivity.Destination.default {
//                EventBus.publish("${item.title}")
                Events.publish(getById(item.itemId))
            }
        }
    }


}