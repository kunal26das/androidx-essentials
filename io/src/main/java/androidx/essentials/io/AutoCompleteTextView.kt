package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.attr.editTextStyle
) : MaterialAutoCompleteTextView(context, attrs, defStyleRes) {

    private var onCutAction: ((Editable?) -> Unit)? = null
    private var onCopyAction: ((Editable?) -> Unit)? = null
    private var onPasteAction: ((Editable?) -> Unit)? = null

    override fun onTextContextMenuItem(id: Int): Boolean {
        return when (id) {
            android.R.id.cut -> {
                onCutAction?.invoke(text)
                super.onTextContextMenuItem(id)
            }
            android.R.id.copy -> {
                onCopyAction?.invoke(text)
                super.onTextContextMenuItem(id)
            }
            android.R.id.paste -> {
                super.onTextContextMenuItem(id).apply {
                    onPasteAction?.invoke(text)
                }
            }
            else -> true
        }
    }

    fun setOnCutListener(action: (Editable?) -> Unit) {
        onCutAction = action
    }

    fun setOnCopyListener(action: (Editable?) -> Unit) {
        onCopyAction = action
    }

    fun setOnPasteListener(action: (Editable?) -> Unit) {
        onPasteAction = action
    }

}