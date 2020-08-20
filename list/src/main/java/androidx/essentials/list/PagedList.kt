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
import kotlin.math.roundToInt

abstract class PagedList<T, V : ViewDataBinding>(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractList<T, V>(context, attrs) {

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
                    context,
                    spanCount,
                    orientation,
                    DEFAULT_REVERSE_LAYOUT
                )
                else -> linearLayoutManager
            }
            showDivider = getBoolean(R.styleable.PagedList_showDivider, DEFAULT_SHOW_DIVIDER)
            marginVertical = getDimension(
                R.styleable.PagedList_marginVertical,
                DEFAULT_MARGIN.toFloat()
            ).roundToInt()
            itemMarginVertical = getDimension(
                R.styleable.PagedList_item_marginVertical,
                DEFAULT_MARGIN.toFloat()
            ).roundToInt()
            itemMarginHorizontal = getDimension(
                R.styleable.PagedList_item_marginHorizontal,
                DEFAULT_MARGIN.toFloat()
            ).roundToInt()
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
                rowCount = calculateRowCount(list)
                layoutManager = mLayoutManager
                dataAdapter.submitList(list)
                dataAdapter
            }
        }
    }

    override val dataAdapter =
        object :
            PagedListAdapter<T, ListItemView.ViewHolder<T, V>>(object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                    return areItemsTheSame(oldItem, newItem)
                }

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                    return areContentsTheSame(oldItem, newItem)
                }
            }) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                onCreateViewHolder(parent)

            override fun onBindViewHolder(holder: ListItemView.ViewHolder<T, V>, position: Int) {
                when (mLayoutManager) {
                    is GridLayoutManager -> setGridLayoutMargins(
                        holder.itemView.layoutParams,
                        position
                    )
                    is LinearLayoutManager -> setLinearLayoutMargins(
                        holder.itemView.layoutParams,
                        position
                    )
                }
                getItem(position)?.apply {
                    holder.bind(this)
                    onBindViewHolder(holder)
                }
            }
        }
}