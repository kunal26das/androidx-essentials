package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.adapter.EmptyAdapter
import androidx.essentials.list.view.ListItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import kotlin.math.roundToInt

abstract class List<T, V : ViewDataBinding>(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractList<T, V>(context, attrs) {

    protected open val emptyMessage = ""

    init {
        context.obtainStyledAttributes(attrs, R.styleable.List, 0, 0).apply {
            orientation =
                when (getInteger(R.styleable.List_android_orientation, DEFAULT_ORIENTATION)) {
                    DEFAULT_ORIENTATION -> VERTICAL
                    else -> HORIZONTAL
                }
            linearLayoutManager = LinearLayoutManager(context, orientation, DEFAULT_REVERSE_LAYOUT)
            val spanCount = getInteger(R.styleable.List_spanCount, DEFAULT_SPAN_COUNT)
            mLayoutManager = when {
                spanCount > 1 -> GridLayoutManager(
                    context,
                    spanCount,
                    orientation,
                    DEFAULT_REVERSE_LAYOUT
                )
                else -> linearLayoutManager
            }
            showDivider = getBoolean(R.styleable.List_showDivider, DEFAULT_SHOW_DIVIDER)
            marginHorizontal =
                getDimension(
                    R.styleable.List_marginHorizontal,
                    DEFAULT_MARGIN.toFloat()
                ).roundToInt()
            marginVertical =
                getDimension(R.styleable.List_marginVertical, DEFAULT_MARGIN.toFloat()).roundToInt()
            itemMarginHorizontal = getDimension(
                R.styleable.List_item_marginHorizontal,
                DEFAULT_MARGIN.toFloat()
            ).roundToInt()
            itemMarginVertical = getDimension(
                R.styleable.List_item_marginVertical,
                DEFAULT_MARGIN.toFloat()
            ).roundToInt()
            recycle()
        }
    }

    fun submitList(list: kotlin.collections.List<T>?) {
        adapter = when {
            list == null -> {
                layoutManager = linearLayoutManager
                loadingAdapter
            }
            list.isEmpty() -> {
                layoutManager = linearLayoutManager
                EmptyAdapter(emptyMessage)
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
        object : ListAdapter<T, ListItemView.ViewHolder<T, V>>(object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return this@List.areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return this@List.areContentsTheSame(oldItem, newItem)
            }
        }) {

            override fun getItemViewType(position: Int) = position

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                onCreateViewHolder(parent)

            override fun onBindViewHolder(holder: ListItemView.ViewHolder<T, V>, position: Int) {
                when (mLayoutManager) {
                    is GridLayoutManager -> setGridLayoutMargins(
                        holder.listItemView.layoutParams, position
                    )
                    is LinearLayoutManager -> setLinearLayoutMargins(
                        holder.listItemView.layoutParams, position
                    )
                }
                getItem(position)?.apply {
                    holder.bind(this)
                    onBindViewHolder(holder)
                }
            }
        }
}