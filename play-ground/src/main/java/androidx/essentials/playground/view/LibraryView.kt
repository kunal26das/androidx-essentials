package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.essentials.list.AbstractList.Companion.DEFAULT_ORIENTATION
import androidx.essentials.list.view.ListItemView
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding

class LibraryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
    attachToRoot: Boolean = DEFAULT_ATTACH_TO_ROOT,
    listOrientation: Int = DEFAULT_ORIENTATION
) : ListItemView<MenuItem, ItemLibraryBinding>(context, attrs, defStyleAttr, attachToRoot) {

    override val binding = ItemLibraryBinding.inflate(
        LayoutInflater.from(context), this, attachToRoot
    )

    init {
        radius = 0f
        elevation = 0f
    }

    override fun bind(item: MenuItem) {
        binding.apply {
            menuItem = item
            executePendingBindings()
            version.text = when (item.title) {
                context.getString(R.string.backdrop) -> androidx.essentials.backdrop.BuildConfig.VERSION_NAME
                context.getString(R.string.core) -> androidx.essentials.core.BuildConfig.VERSION_NAME
                context.getString(R.string.events) -> androidx.essentials.events.BuildConfig.VERSION_NAME
                context.getString(R.string.firebase) -> androidx.essentials.firebase.BuildConfig.VERSION_NAME
                context.getString(R.string.io) -> androidx.essentials.io.BuildConfig.VERSION_NAME
                context.getString(R.string.extensions) -> androidx.essentials.extensions.BuildConfig.VERSION_NAME
                context.getString(R.string.list) -> androidx.essentials.list.BuildConfig.VERSION_NAME
                context.getString(R.string.location) -> androidx.essentials.location.BuildConfig.VERSION_NAME
                context.getString(R.string.network) -> androidx.essentials.network.BuildConfig.VERSION_NAME
                context.getString(R.string.resources) -> androidx.essentials.resources.BuildConfig.VERSION_NAME
                else -> ""
            }
        }
    }

}