package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.view.ListItemView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

abstract class PagedList<T, V : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : AbstractList<T, V>(context, attrs, defStyleAttr) {

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PagedList, 0, 0).apply {
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

    fun submitList(list: PagedList<T>?) {
        adapter = when (list) {
            null -> {
                layoutManager = linearLayoutManager
                loadingAdapter
            }
            else -> {
                layoutManager = mLayoutManager
                dataAdapter.submitList(list)
                dataAdapter
            }
        }
    }

    override val dataAdapter = object : PagedListAdapter<T, ListItemView.ViewHolder<T, V>>(
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) =
                this@PagedList.areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                this@PagedList.areContentsTheSame(oldItem, newItem)
        }
    ) {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ) = this@PagedList.onCreateViewHolder(parent, viewType)

        override fun onBindViewHolder(
            holder: ListItemView.ViewHolder<T, V>, position: Int
        ) {
            getItem(position)?.apply {
                holder.bind(this)
                this@PagedList.onBindViewHolder(holder, position, this)
            }
        }
    }
}