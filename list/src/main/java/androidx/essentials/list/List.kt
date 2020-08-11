package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.adapter.EmptyAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import kotlin.math.roundToInt

abstract class List<T, VDB : ViewDataBinding>(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractList<T, VDB>(context, attrs) {

    init {
        context.obtainStyledAttributes(attrs, R.styleable.List, 0, 0).apply {
            showDivider = getBoolean(R.styleable.List_showDivider, DEFAULT_SHOW_DIVIDER)
            marginVertical =
                getDimension(
                    R.styleable.List_marginVertical,
                    DEFAULT_MARGIN.toFloat()
                ).roundToInt()
            itemMarginVertical =
                getDimension(
                    R.styleable.List_item_marginVertical,
                    DEFAULT_MARGIN.toFloat()
                ).roundToInt()
            itemMarginHorizontal =
                getDimension(
                    R.styleable.List_item_marginHorizontal,
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
        object : ListAdapter<T, ViewHolder<VDB>>(object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return this@List.areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return this@List.areContentsTheSame(oldItem, newItem)
            }
        }) {

            override fun getItemViewType(position: Int) = position

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VDB> {
                return object : ViewHolder<VDB>(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        itemLayout,
                        parent,
                        false
                    )
                ) {}
            }

            override fun onBindViewHolder(holder: ViewHolder<VDB>, position: Int) {
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
                    onBindViewHolder(holder.binding, this)
                }
            }
        }
}