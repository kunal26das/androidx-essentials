package androidx.essentials.playground.autocomplete

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.io.R
import androidx.essentials.io.generic.GenericAutoComplete
import androidx.essentials.playground.Feature

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : GenericAutoComplete<Feature>(context, attrs, defStyleAttr)