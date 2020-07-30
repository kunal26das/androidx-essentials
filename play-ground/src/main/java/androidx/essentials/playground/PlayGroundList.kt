package androidx.essentials.playground

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.list.List
import androidx.essentials.playground.databinding.ItemPlayGroundBinding

class PlayGroundList(
    context: Context,
    attributes: AttributeSet? = null
) : List<Any?, ItemPlayGroundBinding>(context, attributes) {

    override val itemLayout = R.layout.item_play_ground
    override val mLayoutManager = linearLayoutManager

    override fun onBindViewHolder(itemView: ItemPlayGroundBinding, item: Any?) {
        itemView.string = "$item"
    }

}