package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.ScaleAnimation
import android.widget.Checkable
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Coroutines.main
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup(context, attrs, defStyleAttr) {

    private val chipLayout: Int
    private var fromUser = false
    private val isCheckable: Boolean
    private val layoutInflater = LayoutInflater.from(context)
    private var onChipCheckedChangeListener: OnChipCheckedChangeListener? = null

    var array: List<Checkable>? = null
        set(value) {
            field = value?.apply {
                removeAllViews().default {
                    forEachIndexed { index, item ->
                        (layoutInflater.inflate(chipLayout, this@Chips, false) as Chip).main {
                            text = item.toString()
                            isCheckable = this@Chips.isCheckable
                            startAnimation(ScaleAnimation(0f, 1f, 1f, 1f))
                            isChecked = if (isCheckable) {
                                setOnCheckedChangeListener { _, _ ->
                                    onChipCheckedChangeListener?.onChipCheckedChange(index, item)
                                }
                                item.isChecked
                            } else false
                            addView(this)
                        }
                    }
                }
            }
        }

    val selection get() = array?.filter { it.isChecked }

    val isValid: Boolean
        get() = isVisible and when {
            isSelectionRequired -> when {
                selection.isNullOrEmpty() -> {
                    requestFocus()
                    false
                }
                else -> true
            }
            else -> true
        }

    val isInvalid get() = !isValid

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Chips, defStyleAttr, 0).apply {
            isCheckable = getBoolean(R.styleable.Chips_android_checkable, DEFAULT_IS_CHECKABLE)
            chipLayout = getResourceId(R.styleable.Chips_chip, R.layout.chip_action)
            recycle()
        }
    }

    fun setOnChipClickListener(onChipCheckedChangeListener: OnChipCheckedChangeListener) {
        this.onChipCheckedChangeListener = onChipCheckedChangeListener
    }

    fun setOnChipClickListener(onChipClickListener: (index: Int, item: Checkable) -> Unit) {
        setOnChipClickListener(object : OnChipCheckedChangeListener {
            override fun onChipCheckedChange(index: Int, item: Checkable) {
                onChipClickListener(index, item)
            }
        })
    }

    interface OnChipCheckedChangeListener {
        fun onChipCheckedChange(index: Int, item: Checkable)
    }

    companion object {

        const val DEFAULT_IS_CHECKABLE = true

        @JvmStatic
        @BindingAdapter("array")
        fun Chips.setArray(array: List<Checkable>?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.array = array
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "array")
        fun Chips.getArray() = selection

        @JvmStatic
        @BindingAdapter(value = ["arrayAttrChanged"])
        fun Chips.setOnArrayAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            setOnChipClickListener { _, _ ->
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}