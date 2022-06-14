package androidx.essentials.view

import androidx.recyclerview.widget.RecyclerView

class ViewHolder(
    private val listItemView: ListItemView
) : RecyclerView.ViewHolder(listItemView) {
    fun bind(item: Any?): ListItemView {
        return listItemView.apply {
            bind(item)
        }
    }
}