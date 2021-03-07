package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.io.generic.ChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup<String>(context, attrs, defStyleAttr)