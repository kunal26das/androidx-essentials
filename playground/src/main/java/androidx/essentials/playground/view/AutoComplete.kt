package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.io.R
import androidx.essentials.io.generic.GenericAutoComplete
import androidx.essentials.playground.library.Library

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : GenericAutoComplete<Library>(context, attrs, defStyleAttr)