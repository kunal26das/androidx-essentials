package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.essentials.io.R
import androidx.essentials.io.generic.GenericAutoComplete

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : GenericAutoComplete<MenuItem>(context, attrs, defStyleAttr)