package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.essentials.list.adapter.ListStateAdapter
import androidx.essentials.list.internal.AbstractList
import androidx.essentials.list.view.ListItemView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

abstract class PagedList<T, V : ListItemView<T>> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : AbstractList<T, V>(context, attrs, defStyleAttr) {

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PagedList, defStyleAttr, 0).apply {
            val orientation =
                when (getInteger(R.styleable.List_android_orientation, DEFAULT_ORIENTATION)) {
                    DEFAULT_ORIENTATION -> VERTICAL
                    else -> HORIZONTAL
                }
            linearLayoutManager = LinearLayoutManager(context, orientation, DEFAULT_REVERSE_LAYOUT)
            val spanCount = getInteger(R.styleable.PagedList_spanCount, DEFAULT_SPAN_COUNT)
            mLayoutManager = when {
                spanCount > 1 -> GridLayoutManager(
                    context, spanCount, orientation, DEFAULT_REVERSE_LAYOUT
                )
                else -> linearLayoutManager
            }
            showDivider = getBoolean(R.styleable.PagedList_dividers, DEFAULT_SHOW_DIVIDER)
            loadingState = ListStateAdapter(
                getResourceId(R.styleable.PagedList_loadingState, R.layout.layout_loading),
                this@PagedList
            )
            emptyState = ListStateAdapter(
                getResourceId(R.styleable.PagedList_emptyState, R.layout.layout_empty),
                this@PagedList
            )
            recycle()
        }
        adapter = loadingState
    }

    fun submitList(list: PagedList<T>?) = when (list) {
        null -> {
            layoutManager = linearLayoutManager
            adapter = loadingState
        }
        else -> {
            layoutManager = mLayoutManager
            dataAdapter.submitList(list) {
                adapter = dataAdapter
            }
        }
    }

    override val dataAdapter = object : PagedListAdapter<T, ViewHolder>(
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) =
                this@PagedList.areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                this@PagedList.areContentsTheSame(oldItem, newItem)
        }
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateViewHolder

        override fun onBindViewHolder(
            holder: ViewHolder, position: Int
        ) {
            this@PagedList.onBindViewHolder(
                position, getItem(position),
                holder as ListItemView.ViewHolder<T>
            )
        }
    }
}