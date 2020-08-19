package androidx.essentials.list.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.databinding.ViewDataBinding
import androidx.essentials.list.R
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

abstract class ListItemView<T, V : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
    private val attachToRoot: Boolean = true
) : MaterialCardView(context, attrs, defStyleAttr) {

    var item: T? = null
        set(value) {
            field = value
            value?.apply {
                bind(value)
            }
        }

    protected abstract fun bind(item: T)
    abstract val binding: ViewDataBinding
    val viewHolder get() = ViewHolder(this)

    init {
        if (!attachToRoot) {
            layoutParams = MarginLayoutParams(
                MATCH_PARENT, WRAP_CONTENT
            )
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

    class ViewHolder<T, V : ViewDataBinding>(val listItemView: ListItemView<T, V>) :
        RecyclerView.ViewHolder(listItemView) {
        fun bind(item: T) {
            listItemView.item = item
        }
    }

}
