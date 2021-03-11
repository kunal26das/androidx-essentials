package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.children
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.io.generic.GenericChipGroup
import com.google.android.material.chip.Chip

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : GenericChipGroup<String>(context, attrs, defStyleAttr) {

    override var selection: Set<String>?
        get() = super.selection
        set(value) {
            value.default {
                children.forEach {
                    (it as Chip).main {
                        it.isChecked = value?.contains(text) ?: false
                    }
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