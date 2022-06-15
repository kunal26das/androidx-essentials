package androidx.essentials.playground.library

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.essentials.playground.R
import androidx.essentials.view.List

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : List<Library>(context, attrs, defStyleAttr) {

    override val viewHolder get() = LibraryView(context).viewHolder

    override fun onBindViewHolder(
        position: Int,
        item: Library?,
        holder: androidx.essentials.view.ViewHolder<Library>
    ) {
        holder.bind(item)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("list")
        fun Libraries.submitList(
            libraries: kotlin.collections.List<Library>?
        ) = submitList(libraries)

    }

}