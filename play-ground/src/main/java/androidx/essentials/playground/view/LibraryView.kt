package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
    attachToRoot: Boolean = true
) : ListItemView<MenuItem, ItemLibraryBinding>(context, attrs, defStyleAttr, attachToRoot) {

    override val binding = ItemLibraryBinding.inflate(
        LayoutInflater.from(context), this, attachToRoot
    )

    override fun bind(item: MenuItem) {
        binding.apply {
            menuItem = item
            executePendingBindings()
        }
    }

}