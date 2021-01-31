package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.essentials.core.utils.Resources.dp
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
    attachToRoot: Boolean = DEFAULT_ATTACH_TO_ROOT
) : ListItemView<MenuItem, ItemLibraryBinding>(
    context, attrs, defStyleAttr, attachToRoot
) {

    private var onItemClickListener: OnItemClickListener? = null

    override val binding = ItemLibraryBinding.inflate(
        LayoutInflater.from(context), this, attachToRoot
    )

    init {
        radius = 8f.dp
        elevation = 4f.dp
    }

    override fun bind(item: MenuItem) {
        binding.apply {
            menuItem = item
            executePendingBindings()
            root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: MenuItem)
    }

}