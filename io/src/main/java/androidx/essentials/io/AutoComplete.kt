package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.essentials.io.generic.GenericAutoComplete

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : GenericAutoComplete<String>(context, attrs, defStyleAttr)