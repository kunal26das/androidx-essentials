package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

internal class EditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleRes) {

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
                onPasteAction?.invoke(text)
                super.onTextContextMenuItem(id)
            }
            else -> true
        }
    }

    internal fun setOnCutListener(action: (Editable?) -> Unit) {
        onCutAction = action
    }

    internal fun setOnCopyListener(action: (Editable?) -> Unit) {
        onCopyAction = action
    }

    internal fun setOnPasteListener(action: (Editable?) -> Unit) {
        onPasteAction = action
    }

}