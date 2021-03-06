package androidx.essentials.list.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.AbstractList
import androidx.essentials.list.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.card.MaterialCardView

abstract class ListItemView<I> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
    listOrientation: Int = AbstractList.DEFAULT_ORIENTATION
) : MaterialCardView(context, attrs, defStyleAttr) {

    var item: I? = null
        set(value) {
            field = value?.apply {
                bind(value)
            }
        }

    @PublishedApi
    internal val accessLayout
        get() = layout
    protected abstract val layout: Int
    abstract val binding: ViewDataBinding
    val viewHolder by lazy { ViewHolder(this) }

    @PublishedApi
    internal val attachToRoot by lazy { parent is RecyclerView }

    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified T : ViewDataBinding> ListItemView<I>.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout, this, attachToRoot) as T
    }

    init {
        if (!attachToRoot) {
            layoutParams = when (listOrientation) {
                HORIZONTAL -> MarginLayoutParams(WRAP_CONTENT, MATCH_PARENT)
                VERTICAL -> MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                else -> MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            }
        }
        isClickable = !attachToRoot
        isFocusable = !attachToRoot
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!attachToRoot) {
            addView(binding.root)
        }
    }

    override fun onDetachedFromWindow() {
        if (!attachToRoot) {
            removeView(binding.root)
        }
        super.onDetachedFromWindow()
    }

    override fun getLayoutParams() = super.getLayoutParams() as? MarginLayoutParams

    open fun bind(item: I) = binding

    class ViewHolder<T>(
        private val listItemView: ListItemView<T>
    ) : RecyclerView.ViewHolder(listItemView) {
        fun bind(item: T): ListItemView<T> {
            listItemView.item = item
            return listItemView
        }
    }

}
