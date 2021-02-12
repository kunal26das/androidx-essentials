package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup(context, attrs, defStyleAttr) {

    private val chipLayout: Int
    private var isCheckable = DEFAULT_IS_CHECKABLE
    private var onChipCheckedChangeListener: OnChipCheckedChangeListener? = null

    var array = emptyArray<String>()
        set(value) {
            field = value
            removeAllViews()
            selection.clear()
            LayoutInflater.from(context).apply {
                value.forEachIndexed { index, item ->
                    (inflate(chipLayout, this@Chips, false) as Chip).apply {
                        isCheckable = this@Chips.isCheckable
                        if (isCheckable) {
                            setOnCheckedChangeListener { _, isChecked ->
                                when {
                                    isChecked -> selection.add(item)
                                    else -> selection.remove(item)
                                }
                                onChipCheckedChangeListener?.onChipCheckedChange(
                                    index, item, isChecked
                                )
                            }
                        }
                        addView(this)
                        text = item
                    }
                }
            }
        }

    var selection = ArrayList<String>()
        set(value) {
            field = value
            children.forEach { chip ->
                (chip as Chip).apply {
                    chip.isChecked = chip.text in value
                }
            }
        }

    val isValid: Boolean
        get() = isVisible and when {
            isSelectionRequired -> when {
                selection.isEmpty() -> {
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
            array = try {
                context.resources.getStringArray(getResourceIdOrThrow(R.styleable.Chips_array))
            } catch (e: IllegalArgumentException) {
                emptyArray()
            }
            recycle()
        }
    }

    fun setOnChipClickListener(onChipCheckedChangeListener: OnChipCheckedChangeListener) {
        this.onChipCheckedChangeListener = onChipCheckedChangeListener
    }

    fun setOnChipClickListener(onChipClickListener: (index: Int, item: String, isChecked: Boolean) -> Unit) {
        setOnChipClickListener(object : OnChipCheckedChangeListener {
            override fun onChipCheckedChange(index: Int, item: String, isChecked: Boolean) {
                onChipClickListener(index, item, isChecked)
            }
        })
    }

    interface OnChipCheckedChangeListener {
        fun onChipCheckedChange(index: Int, item: String, isChecked: Boolean)
    }

    companion object {

        const val DEFAULT_IS_CHECKABLE = true

        @JvmStatic
        @BindingAdapter("selection")
        fun Chips.setSelection(selection: Array<String>) {
            this.selection = selection.toCollection(ArrayList())
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "selection")
        fun Chips.getSelection(): Array<String> {
            return selection.toTypedArray()
        }

        @JvmStatic
        @BindingAdapter(value = ["selectionAttrChanged"])
        fun Chips.setOnSelectionAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            setOnChipClickListener { _, _, _ ->
                inverseBindingListener.onChange()
            }
        }

    }

}