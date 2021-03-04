package androidx.essentials.io.internal

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.essentials.io.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView

internal class AutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.attr.editTextStyle
) : MaterialAutoCompleteTextView(context, attrs, defStyleRes) {

    internal var filter = DEFAULT_FILTER
    internal var listItem = DEFAULT_LIST_ITEM

    private var onCutAction: ((Editable?) -> Unit)? = null
    private var onCopyAction: ((Editable?) -> Unit)? = null
    private var onPasteAction: ((Editable?) -> Unit)? = null

    internal var array = emptyArray<String>()
        set(value) {
            if (!field.contentEquals(value)) {
                setAdapter(ArrayAdapter(context, listItem, value))
                field = value
            }
        }

    override fun setText(text: CharSequence?, filter: Boolean) {
        super.setText(text, this.filter)
    }

    override fun onTextContextMenuItem(id: Int) = when (id) {
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

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        when {
            filter -> super.performFiltering(text, keyCode)
            array.contains("$text") -> dismissDropDown()
            else -> showDropDown()
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

    companion object {
        const val DEFAULT_FILTER = false
        const val DEFAULT_LIST_ITEM = android.R.layout.simple_list_item_1
    }

}