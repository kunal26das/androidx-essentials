package androidx.essentials.playground.home

import android.view.ViewGroup
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ItemLibraryBinding
import androidx.essentials.view.ViewHolder

class LibraryViewHolder(parent: ViewGroup) :
    ViewHolder<ItemLibraryBinding>(parent, R.layout.item_library) {

    fun bind(item: Library): LibraryViewHolder {
        binding?.library = item
        return this
    }

}