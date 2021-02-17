package androidx.essentials.playground.list

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.essentials.list.List
import androidx.essentials.playground.R
import androidx.essentials.playground.view.LibraryView

class Libraries @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : List<MenuItem, LibraryView>(context, attrs, defStyleAttr) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = LibraryView(context).viewHolder

    companion object {

        @JvmStatic
        @BindingAdapter("libraries")
        fun Libraries.submitList(libraries: kotlin.collections.List<MenuItem>?) {
            submitList(libraries)
        }

    }

}