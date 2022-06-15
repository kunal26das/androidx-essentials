package androidx.essentials.view

import androidx.recyclerview.widget.RecyclerView

class ViewHolder<T>(
    private val listItemView: ListItemView<T>
) : RecyclerView.ViewHolder(listItemView) {
    fun bind(item: T?): ListItemView<T> {
        return listItemView.also {
            it.bind(item)
        }
    }
}