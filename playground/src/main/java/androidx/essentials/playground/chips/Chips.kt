package androidx.essentials.playground.chips

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.essentials.io.R
import androidx.essentials.io.generic.GenericChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : GenericChipGroup<MenuItem>(context, attrs, defStyleAttr)