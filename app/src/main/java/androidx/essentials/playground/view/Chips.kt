package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.essentials.io.R
import androidx.essentials.io.generic.ChipGroup

class Chips @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup<MenuItem>(context, attrs, defStyleAttr)