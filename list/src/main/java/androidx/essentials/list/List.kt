package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Try.Try
import androidx.essentials.list.adapter.EmptyAdapter
import androidx.essentials.list.adapter.LoadingAdapter
import androidx.recyclerview.widget.*
import kotlin.math.roundToInt

abstract class List<T, VDB : ViewDataBinding>(
    context: Context,
    attributes: AttributeSet? = null
) : RecyclerView(context, attributes) {

    open val emptyMessage = ""
    abstract val itemLayout: Int
    var marginVertical: Int = 0
        set(value) {
            field = value
            Try { dataAdapter.notifyDataSetChanged() }
        }
    private var showDivider: Boolean
    private val itemMarginVertical: Int
    private val itemMarginHorizontal: Int
    abstract val mLayoutManager: LayoutManager
    private val loadingAdapter = LoadingAdapter()
    private lateinit var emptyAdapter: EmptyAdapter
    protected val linearLayoutManager = LinearLayoutManager(context)

    init {
        context.obtainStyledAttributes(attributes, R.styleable.List, 0, 0).apply {
            showDivider = getBoolean(R.styleable.List_showDivider, DEFAULT_DIVIDER)
            marginVertical =
                getDimension(R.styleable.List_marginVertical, DEFAULT_MARGIN).roundToInt()
            itemMarginVertical =
                getDimension(R.styleable.List_item_marginVertical, DEFAULT_MARGIN).roundToInt()
            itemMarginHorizontal =
                getDimension(R.styleable.List_item_marginHorizontal, DEFAULT_MARGIN).roundToInt()
            recycle()
        }
        adapter = loadingAdapter
    }

    fun submitList(list: kotlin.collections.List<T>?) {
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

    abstract fun onBindViewHolder(itemView: VDB, item: T)

    open fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    open fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    private val dataAdapter =
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
                if (mLayoutManager is LinearLayoutManager) {
                    (holder.itemView.layoutParams as MarginLayoutParams).default {
                        when (position) {
                            0 -> setMargins(
                                itemMarginHorizontal,
                                itemMarginVertical + marginVertical,
                                itemMarginHorizontal,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                }
                            )
                            itemCount - 1 -> setMargins(
                                itemMarginHorizontal,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                },
                                itemMarginHorizontal,
                                itemMarginVertical + marginVertical
                            )
                            else -> setMargins(
                                itemMarginHorizontal,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                },
                                itemMarginHorizontal,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                }
                            )
                        }
                    }
                }
                getItem(position)?.apply {
                    this@List.onBindViewHolder(holder.binding, this)
                }
            }
        }

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

    abstract class ViewHolder<VDB : ViewDataBinding>(val binding: VDB) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val DEFAULT_MARGIN = 0f
        const val DEFAULT_DIVIDER = false
    }

}