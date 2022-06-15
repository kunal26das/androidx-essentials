package androidx.essentials.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.view.Context.Companion.appCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.card.MaterialCardView

abstract class ListItemView<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
    listOrientation: Int = AbstractList.DEFAULT_ORIENTATION
) : MaterialCardView(context, attrs, defStyleAttr) {

    @PublishedApi
    internal val accessLayout
        get() = layout
    protected abstract val layout: Int
    abstract val binding: ViewDataBinding
    val viewHolder by lazy { ViewHolder<T>(this) }

    @PublishedApi
    internal val attachToRoot by lazy { parent is RecyclerView }
    protected open val activity by lazy { context.appCompatActivity }

    @PublishedApi
    internal val inflater by lazy { LayoutInflater.from(context) }
    inline fun <reified VDB : ViewDataBinding> ListItemView<T>.dataBinding() = lazy {
        DataBindingUtil.inflate(inflater, accessLayout, this, attachToRoot) as VDB
    }

    @MainThread
    protected inline fun <reified VM : ViewModel> ListItemView<T>.viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        return ViewModelLazy(VM::class, {
            activity?.viewModelStore!!
        }, factoryProducer ?: {
            activity?.defaultViewModelProviderFactory!!
        })
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

    abstract fun bind(item: T?)

}
