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
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private var rowCount = 0
    open val emptyMessage = ""
    abstract val itemLayout: Int
    private var showDivider: Boolean
    private val itemMarginVertical: Int
    private val itemMarginHorizontal: Int
    abstract val mLayoutManager: LayoutManager
    private val loadingAdapter = LoadingAdapter()
    private lateinit var emptyAdapter: EmptyAdapter
    protected val linearLayoutManager = LinearLayoutManager(context)

    var marginVertical: Int = 0
        set(value) {
            field = value
            Try { dataAdapter.notifyDataSetChanged() }
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.List, 0, 0).apply {
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
        adapter = when {
            list == null -> {
                layoutManager = linearLayoutManager
                loadingAdapter
            }
            list.isEmpty() -> {
                layoutManager = linearLayoutManager
                emptyAdapter
            }
            else -> {
                rowCount = calculateRowCount(list)
                layoutManager = mLayoutManager
                dataAdapter.submitList(list)
                dataAdapter
            }
        }
    }

    private fun calculateRowCount(list: kotlin.collections.List<T>): Int {
        return when (mLayoutManager) {
            is GridLayoutManager -> {
                val spanCount = (mLayoutManager as GridLayoutManager).spanCount
                (list.size + (list.size % spanCount)) / spanCount
            }
            else -> list.size
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
                    this@List.onBindViewHolder(holder.binding, this)
                }
            }
        }

    private fun setGridLayoutMargins(marginLayoutParams: ViewGroup.LayoutParams, position: Int) {
        val spanCount = (mLayoutManager as GridLayoutManager).spanCount
        (marginLayoutParams as MarginLayoutParams).default {
            val topLeft = 0
            val topRight = spanCount - 1
            val bottomLeft = spanCount * (rowCount - 1)
            val bottomRight = (spanCount * rowCount) - 1
            val corners = setOf(topLeft, topRight, bottomLeft, bottomRight)
            when {
                // Corners
                position in corners -> {
                    when (position) {
                        topLeft -> setMargins(
                            itemMarginHorizontal,
                            itemMarginVertical + marginVertical,
                            itemMarginHorizontal / 2,
                            when {
                                showDivider -> itemMarginVertical
                                else -> itemMarginVertical / 2
                            }
                        )
                        topRight -> setMargins(
                            itemMarginHorizontal / 2,
                            itemMarginVertical + marginVertical,
                            itemMarginHorizontal,
                            when {
                                showDivider -> itemMarginVertical
                                else -> itemMarginVertical / 2
                            }
                        )
                        bottomLeft -> setMargins(
                            itemMarginHorizontal,
                            when {
                                showDivider -> itemMarginVertical
                                else -> itemMarginVertical / 2
                            },
                            itemMarginHorizontal / 2,
                            itemMarginVertical + marginVertical
                        )
                        bottomRight -> setMargins(
                            itemMarginHorizontal / 2,
                            when {
                                showDivider -> itemMarginVertical
                                else -> itemMarginVertical / 2
                            },
                            itemMarginHorizontal,
                            itemMarginVertical + marginVertical
                        )
                    }
                }
                // Top Row
                position in topLeft..topRight -> setMargins(
                    itemMarginHorizontal / 2,
                    itemMarginVertical + marginVertical,
                    itemMarginHorizontal / 2,
                    when {
                        showDivider -> itemMarginVertical
                        else -> itemMarginVertical / 2
                    }
                )
                // Bottom Row
                position in bottomLeft..bottomRight -> setMargins(
                    itemMarginHorizontal / 2,
                    when {
                        showDivider -> itemMarginVertical
                        else -> itemMarginVertical / 2
                    },
                    itemMarginHorizontal / 2,
                    itemMarginVertical + marginVertical
                )
                // Left Column
                position % spanCount == 0 -> setMargins(
                    itemMarginHorizontal,
                    when {
                        showDivider -> itemMarginVertical
                        else -> itemMarginVertical / 2
                    },
                    itemMarginHorizontal / 2,
                    when {
                        showDivider -> itemMarginVertical
                        else -> itemMarginVertical / 2
                    }
                )
                // Right Column
                position % topRight == 0 -> setMargins(
                    itemMarginHorizontal / 2,
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
                // Center
                else -> setMargins(
                    itemMarginHorizontal / 2,
                    when {
                        showDivider -> itemMarginVertical
                        else -> itemMarginVertical / 2
                    },
                    itemMarginHorizontal / 2,
                    when {
                        showDivider -> itemMarginVertical
                        else -> itemMarginVertical / 2
                    }
                )
            }
        }
    }

    private fun setLinearLayoutMargins(layoutParams: ViewGroup.LayoutParams, position: Int) {
        (layoutParams as MarginLayoutParams).default {
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
                rowCount - 1 -> setMargins(
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutManager = mLayoutManager
        emptyAdapter = EmptyAdapter(emptyMessage)
        if (showDivider) {
            when (mLayoutManager) {
                is GridLayoutManager -> {
                    addItemDecoration(
                        DividerItemDecoration(
                            context,
                            (mLayoutManager as GridLayoutManager).orientation
                        )
                    )
                }
                is LinearLayoutManager -> {
                    addItemDecoration(
                        DividerItemDecoration(
                            context,
                            (mLayoutManager as LinearLayoutManager).orientation
                        )
                    )
                }
            }
        }
    }

    abstract class ViewHolder<VDB : ViewDataBinding>(val binding: VDB) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val DEFAULT_MARGIN = 0f
        const val DEFAULT_DIVIDER = false
    }

}