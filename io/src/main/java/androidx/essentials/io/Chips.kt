package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.getResourceIdOrThrow
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup(context, attrs, defStyleAttr) {

    private val chipLayout: Int
    var selection = ArrayList<String>()
        internal set
    private var onChipCheckedChangeListener: OnChipCheckedChangeListener? = null

    var array = emptyArray<String>()
        set(value) {
            field = value
            removeAllViews()
            selection.clear()
            LayoutInflater.from(context).apply {
                value.forEachIndexed { index, item ->
                    (inflate(chipLayout, this@Chips, false) as Chip).apply {
                        setOnCheckedChangeListener { _, isChecked ->
                            when {
                                isChecked -> selection.add(item)
                                else -> selection.remove(item)
                            }
                            onChipCheckedChangeListener?.onChipCheckedChange(index, item, isChecked)
                        }
                        addView(this)
                        text = item
                    }
                }
            }
        }

    val isValid: Boolean
        get() {
            return when {
                isSelectionRequired -> when {
                    selection.isEmpty() -> {
                        requestFocus()
                        false
                    }
                    else -> true
                }
                else -> true
            }
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Chips, defStyleAttr, 0).apply {
            chipLayout = when (getInt(R.styleable.Chips_type, STYLE_ACTION)) {
                STYLE_CHOICE -> R.layout.layout_chip_choice
                STYLE_ENTRY -> R.layout.layout_chip_entry
                STYLE_FILTER -> R.layout.layout_chip_filter
                else -> R.layout.layout_chip_action
            }
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
        this.onChipCheckedChangeListener = object : OnChipCheckedChangeListener {
            override fun onChipCheckedChange(index: Int, item: String, isChecked: Boolean) {
                onChipClickListener(index, item, isChecked)
            }
        }
    }

    interface OnChipCheckedChangeListener {
        fun onChipCheckedChange(index: Int, item: String, isChecked: Boolean)
    }

    companion object {
        const val STYLE_ACTION = 0
        const val STYLE_CHOICE = 1
        const val STYLE_ENTRY = 2
        const val STYLE_FILTER = 3
    }

}