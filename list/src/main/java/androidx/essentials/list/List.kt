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

abstract class List<T, V : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : AbstractList<T, V>(context, attrs, defStyleAttr) {

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
                    context, spanCount, orientation, DEFAULT_REVERSE_LAYOUT
                )
                else -> linearLayoutManager
            }
            showDivider = getBoolean(R.styleable.List_showDivider, DEFAULT_SHOW_DIVIDER)
            recycle()
        }
    }

    fun submitList(list: kotlin.collections.List<T>?) {
        when {
            list == null -> {
                layoutManager = linearLayoutManager
                adapter = loadingAdapter
            }
            list.isEmpty() -> {
                layoutManager = linearLayoutManager
                adapter = EmptyAdapter(emptyMessage)
            }
            else -> {
                layoutManager = mLayoutManager
                dataAdapter.submitList(list) {
                    adapter = dataAdapter
                }
            }
        }
    }

    override val dataAdapter = object : ListAdapter<T, ListItemView.ViewHolder<T, V>>(
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) =
                this@List.areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                this@List.areContentsTheSame(oldItem, newItem)
        }
    ) {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ) = this@List.onCreateViewHolder(parent, viewType)

        override fun onBindViewHolder(
            holder: ListItemView.ViewHolder<T, V>, position: Int
        ) {
            getItem(position)?.apply {
                holder.bind(this)
                this@List.onBindViewHolder(holder, position, this)
            }
        }

    }
}