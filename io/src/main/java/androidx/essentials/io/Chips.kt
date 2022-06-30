package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.children
import com.google.android.material.chip.Chip

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : GenericChipGroup<String>(context, attrs, defStyleAttr) {

    override var selection: Set<String>?
        get() = super.selection
        set(value) {
            children.forEach {
                (it as Chip).apply {
                    it.isChecked = value?.contains(text) == true
                }
            }
        }

    override var inverseSelection: Set<String>?
        get() = super.inverseSelection
        set(value) {
            children.forEach {
                (it as Chip).apply {
                    it.isChecked = value?.contains(text) == false
                }
            }
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Chips, defStyleAttr, 0).apply {
            try {
                chips =
                    resources.getStringArray(getResourceIdOrThrow(R.styleable.Chips_chips)).toSet()
            } catch (e: IllegalArgumentException) {
            }
            recycle()
        }
    }

}