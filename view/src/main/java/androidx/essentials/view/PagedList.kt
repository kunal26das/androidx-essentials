package androidx.essentials.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

abstract class PagedList<T : Any> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : AbstractList<T>(context, attrs, defStyleAttr) {

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
            recycle()
        }
    }

    suspend fun submitList(list: PagingData<T>?) = when (list) {
        null -> {
            layoutManager = linearLayoutManager
        }
        else -> {
            layoutManager = mLayoutManager
            dataAdapter.submitData(list)
        }
    }

    override val dataAdapter =
        object : PagingDataAdapter<T, androidx.essentials.view.ViewHolder<T>>(
            object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T) =
                    this@PagedList.areItemsTheSame(oldItem, newItem)

                override fun areContentsTheSame(oldItem: T, newItem: T) =
                    this@PagedList.areContentsTheSame(oldItem, newItem)
            }
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = viewHolder

        override fun onBindViewHolder(
            holder: androidx.essentials.view.ViewHolder<T>, position: Int
        ) {
            this@PagedList.onBindViewHolder(
                position, getItem(position), holder
            )
        }
    }
}