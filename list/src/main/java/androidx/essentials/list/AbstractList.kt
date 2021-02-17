package androidx.essentials.list

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.essentials.list.adapter.ListStateAdapter
import androidx.essentials.list.view.ListItemView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractList<T, V : ListItemView<T>> @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : RecyclerView(context, attributes, defStyleAttr) {

    internal var showDivider = DEFAULT_SHOW_DIVIDER
    protected var orientation = DEFAULT_ORIENTATION
    protected val reverseLayout = DEFAULT_REVERSE_LAYOUT

    lateinit var emptyState: ListStateAdapter
    lateinit var loadingState: ListStateAdapter
    abstract val dataAdapter: Adapter<ViewHolder>
    internal lateinit var mLayoutManager: LayoutManager
    internal lateinit var linearLayoutManager: LinearLayoutManager

    init {
        clipToPadding = false
        overScrollMode = OVER_SCROLL_NEVER
        layoutManager = LinearLayoutManager(context)
    }

    abstract val onCreateViewHolder: ListItemView.ViewHolder<T>

    @CallSuper
    open fun onBindViewHolder(
        position: Int, item: T?,
        holder: ListItemView.ViewHolder<T>
    ) = item?.let { holder.bind(it) } as V

    open fun areItemsTheSame(oldItem: T, newItem: T) = false

    open fun areContentsTheSame(oldItem: T, newItem: T) = false

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (showDivider) {
            when (mLayoutManager) {
                is GridLayoutManager -> {
                    addItemDecoration(DividerItemDecoration(context, VERTICAL))
                    addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
                }
                else -> addItemDecoration(DividerItemDecoration(context, orientation))
            }
        }
    }

    companion object {
        const val DEFAULT_ORIENTATION = VERTICAL
        internal const val DEFAULT_SPAN_COUNT = 1
        internal const val DEFAULT_SHOW_DIVIDER = false
        internal const val DEFAULT_REVERSE_LAYOUT = false
    }

}
