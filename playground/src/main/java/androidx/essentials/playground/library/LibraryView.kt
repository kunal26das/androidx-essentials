package androidx.essentials.playground.library

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.setMargins
import androidx.essentials.playground.R
import androidx.essentials.playground.Resources.dp
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.view.ListItemView

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : ListItemView<Library>(context, attrs, defStyleAttr) {

    override val layout = R.layout.item_library
    override val binding by dataBinding<ItemLibraryBinding>()

    init {
        radius = 4f.dp
        elevation = radius
        layoutParams?.setMargins(8.dp)
    }

    override fun bind(item: Library?) {
        binding.library = item
        binding.executePendingBindings()
    }

}