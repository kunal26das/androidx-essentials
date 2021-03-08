package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.essentials.io.generic.GenericAutoComplete

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : GenericAutoComplete<String>(context, attrs, defStyleAttr) {

    init {
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            try {
                array =
                    resources.getStringArray(getResourceIdOrThrow(R.styleable.AutoComplete_array))
            } catch (e: IllegalArgumentException) {
            }
            recycle()
        }
    }

}