package androidx.essentials.io.generic

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.animation.ScaleAnimation
import android.widget.Checkable
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.io.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import androidx.essentials.io.generic.ChipGroup as GenericChipGroup

abstract class ChipGroup<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup(context, attrs, defStyleAttr) {

    private val chipLayout: Int
    internal var fromUser = false
    private val isCheckable: Boolean
    private val inflater = LayoutInflater.from(context)
    private var onChipCheckedChangeListener: OnChipCheckedChangeListener<T>? = null

    var array: List<T>? = null
        set(value) {
            field = value?.apply {
                removeAllViews().default {
                    forEachIndexed { index, item ->
                        (inflater.inflate(chipLayout, this@ChipGroup, false) as Chip).main {
                            startAnimation(ScaleAnimation(0f, 1f, 1f, 1f))
                            isCheckable = this@ChipGroup.isCheckable
                            isChecked = when (item) {
                                is Checkable -> item.isChecked
                                is MenuItem -> item.isChecked
                                else -> false
                            }
                            text = item.toString()
                            addView(this)
                            if (isCheckable) {
                                setOnCheckedChangeListener { _, _ ->
                                    onChipCheckedChangeListener?.onChipCheckedChange(index, item)
                                }
                            }
                        }
                    }
                }
            }
        }

    val selection
        get() = array?.filter {
            when (it) {
                is Checkable -> it.isChecked
                is MenuItem -> it.isChecked
                else -> false
            }
        }

    val isValid: Boolean
        get() = isVisible and
            isSelectionRequired and
            !selection.isNullOrEmpty()

    val isInvalid get() = !isValid

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ChipGroup, defStyleAttr, 0).apply {
            isCheckable = getBoolean(R.styleable.ChipGroup_android_checkable, DEFAULT_IS_CHECKABLE)
            chipLayout = getResourceId(R.styleable.ChipGroup_chip, R.layout.chip_action)
            recycle()
        }
    }

    fun setOnChipClickListener(onChipCheckedChangeListener: OnChipCheckedChangeListener<T>) {
        this.onChipCheckedChangeListener = onChipCheckedChangeListener
    }

    fun setOnChipClickListener(onChipClickListener: (index: Int, item: T) -> Unit) {
        setOnChipClickListener(object : OnChipCheckedChangeListener<T> {
            override fun onChipCheckedChange(index: Int, item: T) {
                onChipClickListener(index, item)
            }
        })
    }

    interface OnChipCheckedChangeListener<T> {
        fun onChipCheckedChange(index: Int, item: T)
    }

    companion object {

        const val DEFAULT_IS_CHECKABLE = true

        @JvmStatic
        @BindingAdapter("array")
        fun <T> GenericChipGroup<T>.setArray(array: List<T>?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.array = array
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "array")
        fun <T> GenericChipGroup<T>.getArray() = selection

        @JvmStatic
        @BindingAdapter(value = ["arrayAttrChanged"])
        fun <T> GenericChipGroup<T>.setOnArrayAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            setOnChipClickListener { _, _ ->
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}