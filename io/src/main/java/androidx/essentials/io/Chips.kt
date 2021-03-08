package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.io.generic.GenericChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : GenericChipGroup<String>(context, attrs, defStyleAttr)