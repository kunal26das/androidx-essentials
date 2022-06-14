package androidx.essentials.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter

abstract class List<T : Any> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : AbstractList<T>(context, attrs, defStyleAttr) {

    init {
        context.obtainStyledAttributes(attrs, R.styleable.List, defStyleAttr, 0).apply {
            orientation =
                when (getInteger(R.styleable.List_android_orientation, DEFAULT_ORIENTATION)) {
                    DEFAULT_ORIENTATION -> VERTICAL
                    else -> HORIZONTAL
                }
            linearLayoutManager = LinearLayoutManager(context, orientation, reverseLayout)
            val spanCount = getInteger(R.styleable.List_spanCount, DEFAULT_SPAN_COUNT)
            mLayoutManager = when {
                spanCount > 1 -> GridLayoutManager(context, spanCount, orientation, reverseLayout)
                else -> linearLayoutManager
            }
            showDivider = getBoolean(R.styleable.List_dividers, DEFAULT_SHOW_DIVIDER)
            recycle()
        }
    }

    fun submitList(list: kotlin.collections.List<T>?) = when {
        list == null -> {
            layoutManager = linearLayoutManager
        }
        list.isEmpty() -> {
            layoutManager = linearLayoutManager
        }
        else -> {
            layoutManager = mLayoutManager
            dataAdapter.submitList(list) {
                adapter = dataAdapter
            }
        }
    }

    override val dataAdapter = object : ListAdapter<T, ViewHolder>(
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) =
                this@List.areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                this@List.areContentsTheSame(oldItem, newItem)
        }
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = viewHolder

        override fun onBindViewHolder(
            holder: ViewHolder, position: Int
        ) = this@List.onBindViewHolder(
            position, getItem(position), holder
        )
    }
}