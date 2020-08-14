package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Try.Try
import androidx.essentials.list.adapter.LoadingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractList<T, VDB : ViewDataBinding>(
    context: Context,
    attributes: AttributeSet? = null
) : RecyclerView(context, attributes) {

    internal var rowCount = 0
    abstract val itemLayout: Int
    abstract val mLayoutManager: LayoutManager
    internal val loadingAdapter = LoadingAdapter()
    abstract val dataAdapter: Adapter<ViewHolder<VDB>>
    internal var itemMarginVertical = DEFAULT_MARGIN
    internal var itemMarginHorizontal = DEFAULT_MARGIN
    internal var showDivider = DEFAULT_SHOW_DIVIDER
    protected val linearLayoutManager = LinearLayoutManager(context)

    var marginVertical: Int = 0
        set(value) {
            field = value
            Try { dataAdapter.notifyDataSetChanged() }
        }

    init {
        adapter = loadingAdapter
    }

    abstract fun onBindViewHolder(itemView: VDB, item: T)

    internal fun calculateRowCount(list: kotlin.collections.List<T>): Int {
        return when (mLayoutManager) {
            is GridLayoutManager -> {
                val spanCount = (mLayoutManager as GridLayoutManager).spanCount
                (list.size + (list.size % spanCount)) / spanCount
            }
            else -> list.size
        }
    }

    open fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    open fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    internal fun setGridLayoutMargins(marginLayoutParams: ViewGroup.LayoutParams, position: Int) {
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

    internal fun setLinearLayoutMargins(layoutParams: ViewGroup.LayoutParams, position: Int) {
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
        const val DEFAULT_MARGIN = 0
        const val DEFAULT_SHOW_DIVIDER = false
    }

}