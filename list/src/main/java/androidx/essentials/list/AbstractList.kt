package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.list.adapter.LoadingAdapter
import androidx.essentials.list.view.ListItemView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractList<T, V : ViewDataBinding>(
    context: Context,
    attributes: AttributeSet? = null
) : RecyclerView(context, attributes) {

    var marginVertical = DEFAULT_MARGIN
    lateinit var mLayoutManager: LayoutManager
    internal var rowCount = DEFAULT_ROW_COUNT
    internal val loadingAdapter = LoadingAdapter()
    internal var itemMarginVertical = DEFAULT_MARGIN
    internal var itemMarginHorizontal = DEFAULT_MARGIN
    internal var showDivider = DEFAULT_SHOW_DIVIDER
    protected val linearLayoutManager = LinearLayoutManager(context)
    abstract val dataAdapter: Adapter<ListItemView.ViewHolder<T, V>>

    init {
        adapter = loadingAdapter
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): ListItemView.ViewHolder<T, V>

    open fun onBindViewHolder(holder: ListItemView.ViewHolder<T, V>) {}

    internal fun calculateRowCount(list: kotlin.collections.List<T>): Int {
        return when (mLayoutManager) {
            is GridLayoutManager -> {
                val spanCount = (mLayoutManager as GridLayoutManager).spanCount
                (list.size / spanCount) + (list.size % spanCount)
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
                        topLeft -> {
                            log(position, "Top Left")
                            setMargins(
                                itemMarginHorizontal,
                                itemMarginVertical + marginVertical,
                                itemMarginHorizontal / 2,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                }
                            )
                        }
                        topRight -> {
                            log(position, "Top Right")
                            setMargins(
                                itemMarginHorizontal / 2,
                                itemMarginVertical + marginVertical,
                                itemMarginHorizontal,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                }
                            )
                        }
                        bottomLeft -> {
                            log(position, "Bottom Left")
                            setMargins(
                                itemMarginHorizontal,
                                when {
                                    showDivider -> itemMarginVertical
                                    else -> itemMarginVertical / 2
                                },
                                itemMarginHorizontal / 2,
                                itemMarginVertical + marginVertical
                            )
                        }
                        bottomRight -> {
                            log(position, "Bottom Right")
                            setMargins(
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
                }
                // Top Row
                position in topLeft..topRight -> {
                    log(position, "Top Row")
                    setMargins(
                        itemMarginHorizontal / 2,
                        itemMarginVertical + marginVertical,
                        itemMarginHorizontal / 2,
                        when {
                            showDivider -> itemMarginVertical
                            else -> itemMarginVertical / 2
                        }
                    )
                }
                // Bottom Row
                position in bottomLeft..bottomRight -> {
                    log(position, "Bottom Row")
                    setMargins(
                        itemMarginHorizontal / 2,
                        when {
                            showDivider -> itemMarginVertical
                            else -> itemMarginVertical / 2
                        },
                        itemMarginHorizontal / 2,
                        itemMarginVertical + marginVertical
                    )
                }
                // Left Column
                position % spanCount == 0 -> {
                    log(position, "Left Column")
                    setMargins(
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
                }
                // Right Column
                (position + 1) % spanCount == 0 -> {
                    log(position, "Right Column")
                    setMargins(
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
                }
                // Center
                else -> {
                    log(position, "Center")
                    setMargins(
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

    private fun log(position: Int, place: String) {
        Log.d(javaClass.simpleName, "$position: $place")
    }

    companion object {
        internal const val DEFAULT_MARGIN = 0
        internal const val DEFAULT_ROW_COUNT = 0
        internal const val DEFAULT_SPAN_COUNT = 1
        internal const val DEFAULT_SHOW_DIVIDER = false
    }

}