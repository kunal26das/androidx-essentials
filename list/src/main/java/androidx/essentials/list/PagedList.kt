package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.math.roundToInt

abstract class PagedList<T, VDB : ViewDataBinding>(
    context: Context,
    attributes: AttributeSet? = null
) : AbstractList<T, VDB>(context, attributes) {

    init {
        context.obtainStyledAttributes(attributes, R.styleable.PagedList, 0, 0).apply {
            showDivider = getBoolean(
                R.styleable.PagedList_showDivider,
                DEFAULT_SHOW_DIVIDER
            )
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
        adapter = when {
            list == null -> {
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
        object : PagedListAdapter<T, ViewHolder<VDB>>(object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return areContentsTheSame(oldItem, newItem)
            }
        }) {

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