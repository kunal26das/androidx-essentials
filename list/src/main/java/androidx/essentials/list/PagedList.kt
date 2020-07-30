package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.adapter.EmptyAdapter
import androidx.essentials.list.adapter.LoadingAdapter
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagedList<T, VDB : ViewDataBinding>(
    context: Context,
    attributes: AttributeSet? = null
) : RecyclerView(context, attributes) {

    open val emptyMessage = ""
    abstract val itemLayout: Int
    private var showDivider: Boolean
    abstract val mLayoutManager: LayoutManager
    private val loadingAdapter = LoadingAdapter()
    private lateinit var emptyAdapter: EmptyAdapter
    protected val linearLayoutManager = LinearLayoutManager(context)

    init {
        context.obtainStyledAttributes(attributes, R.styleable.PagedList, 0, 0).apply {
            showDivider = getBoolean(R.styleable.PagedList_showDivider, DEFAULT_DIVIDER)
            recycle()
        }
        adapter = loadingAdapter
    }

    fun submitList(list: PagedList<T>?) {
        when {
            list == null -> {
                layoutManager = linearLayoutManager
                adapter = loadingAdapter
            }
            list.isEmpty() -> {
                layoutManager = linearLayoutManager
                adapter = emptyAdapter
            }
            else -> {
                layoutManager = mLayoutManager
                dataAdapter.submitList(list)
                adapter = dataAdapter
            }
        }
    }

    open fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    open fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    private val dataAdapter =
        object : PagedListAdapter<T, ViewHolder<VDB>>(object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return this@PagedList.areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return this@PagedList.areContentsTheSame(oldItem, newItem)
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
                this@PagedList.onBindViewHolder(holder.root, getItem(position))
            }
        }

    abstract fun onBindViewHolder(itemView: VDB, item: T?)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutManager = mLayoutManager
        emptyAdapter = EmptyAdapter(emptyMessage)
        if (showDivider && mLayoutManager is LinearLayoutManager) {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (mLayoutManager as LinearLayoutManager).orientation
                )
            )
        }
    }

    abstract class ViewHolder<VDB : ViewDataBinding>(val root: VDB) :
        RecyclerView.ViewHolder(root.root)

    companion object {
        const val DEFAULT_DIVIDER = false
    }

}