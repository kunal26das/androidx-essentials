package androidx.essentials.playground.home.library

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.core.view.setMargins
import androidx.essentials.playground.R
import androidx.essentials.playground.Resources.dp
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.ui.ListItemView

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : ListItemView(context, attrs, defStyleAttr) {

    override val layout = R.layout.item_library
    override val binding by dataBinding<ItemLibraryBinding>()

    init {
        radius = 4f.dp
        elevation = radius
        layoutParams?.setMargins(8.dp)
    }

    override fun bind(item: Any?): ItemLibraryBinding {
        binding.menuItem = item as? MenuItem
        binding.executePendingBindings()
        return binding
    }

}