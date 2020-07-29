package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.adapter.EmptyAdapter
import androidx.essentials.list.adapter.LoadingAdapter
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerView<T, VDB : ViewDataBinding>(
    context: Context,
    attributes: AttributeSet? = null
) : RecyclerView(context, attributes) {

    abstract val itemLayout: Int
    abstract val emptyMessage: String
    private val loadingAdapter = LoadingAdapter()
    private lateinit var emptyAdapter: EmptyAdapter
    private val linearLayoutManager = LinearLayoutManager(context)

    val currentList: List<T>?
        get() = dataAdapter.currentList

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
    abstract fun onBindViewHolder(holder: ViewHolder<VDB>, item: T)
    abstract class ViewHolder<VDB : ViewDataBinding>(val root: VDB) :
        RecyclerView.ViewHolder(root.root)

    init {
        adapter = loadingAdapter
        layoutManager = linearLayoutManager
    }

    fun submitList(list: List<T>?) {
        adapter = when {
            list == null -> loadingAdapter
            list.isEmpty() -> emptyAdapter
            else -> {
                dataAdapter.submitList(list)
                dataAdapter
            }
        }
    }

    protected fun getItem(position: Int) = dataAdapter.currentList[position]

    private val dataAdapter =
        object : ListAdapter<T, ViewHolder<VDB>>(object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return this@RecyclerView.areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return this@RecyclerView.areContentsTheSame(oldItem, newItem)
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
                this@RecyclerView.onBindViewHolder(holder, getItem(position))
            }
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        emptyAdapter = EmptyAdapter(emptyMessage)
        addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
    }
}