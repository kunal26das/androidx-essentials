package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
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

    internal var rowCount = DEFAULT_ROW_COUNT
    internal val loadingAdapter = LoadingAdapter()
    internal var marginVertical = DEFAULT_MARGIN
    internal var marginHorizontal = DEFAULT_MARGIN
    internal lateinit var mLayoutManager: LayoutManager
    protected var orientation = DEFAULT_ORIENTATION
    internal var itemMarginVertical = DEFAULT_MARGIN
    internal var itemMarginHorizontal = DEFAULT_MARGIN
    internal var showDivider = DEFAULT_SHOW_DIVIDER
    internal lateinit var linearLayoutManager: LinearLayoutManager
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
        val orientation = (mLayoutManager as GridLayoutManager).orientation
        val topLeft = 0
        val topRight = spanCount - 1
        val bottomLeft = spanCount * (rowCount - 1)
        val bottomRight = (spanCount * rowCount) - 1
        val corners = setOf(topLeft, topRight, bottomLeft, bottomRight)
        (marginLayoutParams as MarginLayoutParams).apply {
            when (orientation) {
                VERTICAL -> when {
                    // Corners
                    position in corners -> {
                        when (position) {
                            topLeft -> {
                                log(position, "Top Left")
                                setMargins(
                                    itemMarginHorizontal + marginHorizontal,
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
                                    itemMarginHorizontal + marginHorizontal,
                                    when {
                                        showDivider -> itemMarginVertical
                                        else -> itemMarginVertical / 2
                                    }
                                )
                            }
                            bottomLeft -> {
                                log(position, "Bottom Left")
                                setMargins(
                                    itemMarginHorizontal + marginHorizontal,
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
                                    itemMarginHorizontal + marginHorizontal,
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
                            itemMarginHorizontal + marginHorizontal,
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
                            itemMarginHorizontal + marginHorizontal,
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
                HORIZONTAL -> when {
                    // Corners
                    position in corners -> {
                        when (position) {
                            topLeft -> {
                                log(position, "Top Left")
                                setMargins(
                                    itemMarginHorizontal + marginHorizontal,
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
                                    itemMarginHorizontal + marginHorizontal,
                                    when {
                                        showDivider -> itemMarginVertical
                                        else -> itemMarginVertical / 2
                                    }
                                )
                            }
                            bottomLeft -> {
                                log(position, "Bottom Left")
                                setMargins(
                                    itemMarginHorizontal + marginHorizontal,
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
                                    itemMarginHorizontal + marginHorizontal,
                                    itemMarginVertical + marginVertical
                                )
                            }
                        }
                    }
                    // Left Column
                    position in topLeft..topRight -> {
                        log(position, "Left Column")
                        setMargins(
                            itemMarginHorizontal + marginHorizontal,
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
                    position in bottomLeft..bottomRight -> {
                        log(position, "Right Column")
                        setMargins(
                            itemMarginHorizontal / 2,
                            when {
                                showDivider -> itemMarginVertical
                                else -> itemMarginVertical / 2
                            },
                            itemMarginHorizontal + marginHorizontal,
                            when {
                                showDivider -> itemMarginVertical
                                else -> itemMarginVertical / 2
                            }
                        )
                    }
                    // Top Row
                    position % spanCount == 0 -> {
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
                    (position + 1) % spanCount == 0 -> {
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
    }

    internal fun setLinearLayoutMargins(layoutParams: ViewGroup.LayoutParams, position: Int) {
        val orientation = (mLayoutManager as LinearLayoutManager).orientation
        (layoutParams as MarginLayoutParams).apply {
            when (orientation) {
                VERTICAL -> when (position) {
                    0 -> setMargins(
                        itemMarginHorizontal + marginHorizontal,
                        itemMarginVertical + marginVertical,
                        itemMarginHorizontal + marginHorizontal,
                        when {
                            showDivider -> itemMarginVertical
                            else -> itemMarginVertical / 2
                        }
                    )
                    rowCount - 1 -> setMargins(
                        itemMarginHorizontal + marginHorizontal,
                        when {
                            showDivider -> itemMarginVertical
                            else -> itemMarginVertical / 2
                        },
                        itemMarginHorizontal + marginHorizontal,
                        itemMarginVertical + marginVertical
                    )
                    else -> setMargins(
                        itemMarginHorizontal + marginHorizontal,
                        when {
                            showDivider -> itemMarginVertical
                            else -> itemMarginVertical / 2
                        },
                        itemMarginHorizontal + marginHorizontal,
                        when {
                            showDivider -> itemMarginVertical
                            else -> itemMarginVertical / 2
                        }
                    )
                }
                HORIZONTAL -> when (position) {
                    0 -> setMargins(
                        itemMarginHorizontal + marginHorizontal,
                        itemMarginVertical + marginVertical,
                        when {
                            showDivider -> itemMarginHorizontal
                            else -> itemMarginHorizontal / 2
                        },
                        itemMarginVertical + marginVertical
                    )
                    rowCount - 1 -> setMargins(
                        when {
                            showDivider -> itemMarginHorizontal
                            else -> itemMarginHorizontal / 2
                        },
                        itemMarginVertical + marginVertical,
                        itemMarginHorizontal + marginHorizontal,
                        itemMarginVertical + marginVertical
                    )
                    else -> setMargins(
                        when {
                            showDivider -> itemMarginHorizontal
                            else -> itemMarginHorizontal / 2
                        },
                        itemMarginVertical + marginVertical,
                        when {
                            showDivider -> itemMarginHorizontal
                            else -> itemMarginHorizontal / 2
                        },
                        itemMarginVertical + marginVertical
                    )
                }
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
        const val DEFAULT_ORIENTATION = VERTICAL
        internal const val DEFAULT_SHOW_DIVIDER = false
        internal const val DEFAULT_REVERSE_LAYOUT = false
    }

}
