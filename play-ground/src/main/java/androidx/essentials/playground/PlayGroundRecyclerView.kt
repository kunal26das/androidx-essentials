package androidx.essentials.playground

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.list.RecyclerView
import androidx.essentials.playground.databinding.ItemStringBinding

class PlayGroundRecyclerView(
    context: Context,
    attributes: AttributeSet? = null
) : RecyclerView<Any?, ItemStringBinding>(context, attributes) {

    override val emptyMessage = "Empty Play Ground"
    override val itemLayout = R.layout.item_string

    override fun areContentsTheSame(oldItem: Any?, newItem: Any?) =
        false

    override fun areItemsTheSame(oldItem: Any?, newItem: Any?) =
        false

    override fun onBindViewHolder(
        holder: ViewHolder<ItemStringBinding>,
        item: Any?
    ) {
        holder.root.string = "$item"
    }

}