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

abstract class AbstractList<T, V : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : RecyclerView(context, attributes, defStyleAttr) {

    internal val loadingAdapter = LoadingAdapter()
    internal var showDivider = DEFAULT_SHOW_DIVIDER
    protected var orientation = DEFAULT_ORIENTATION
    internal lateinit var mLayoutManager: LayoutManager
    internal lateinit var linearLayoutManager: LinearLayoutManager
    abstract val dataAdapter: Adapter<ListItemView.ViewHolder<T, V>>

    init {
        adapter = loadingAdapter
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): ListItemView.ViewHolder<T, V>

    open fun onBindViewHolder(
        holder: ListItemView.ViewHolder<T, V>,
        position: Int, item: T
    ) {
    }

    open fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    open fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutManager = mLayoutManager
        if (showDivider) {
            when (mLayoutManager) {
                is GridLayoutManager -> addItemDecoration(
                    DividerItemDecoration(
                        context,
                        (mLayoutManager as GridLayoutManager).orientation
                    )
                )
                is LinearLayoutManager -> addItemDecoration(
                    DividerItemDecoration(
                        context,
                        (mLayoutManager as LinearLayoutManager).orientation
                    )
                )
            }
        }
    }

    private fun log(position: Int, place: String) {
        Log.d(javaClass.simpleName, "$position: $place")
    }

    companion object {
        const val DEFAULT_ORIENTATION = VERTICAL
        internal const val DEFAULT_SPAN_COUNT = 1
        internal const val DEFAULT_SHOW_DIVIDER = false
        internal const val DEFAULT_REVERSE_LAYOUT = false
    }

}
