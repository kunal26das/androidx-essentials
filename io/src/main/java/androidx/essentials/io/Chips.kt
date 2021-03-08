package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.essentials.io.generic.GenericChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : GenericChipGroup<String>(context, attrs, defStyleAttr) {

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