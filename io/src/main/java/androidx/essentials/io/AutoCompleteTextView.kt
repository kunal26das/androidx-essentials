package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.essentials.io.Field.Companion.DEFAULT_IS_EDITABLE
import com.google.android.material.textfield.MaterialAutoCompleteTextView

internal class AutoCompleteTextView<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.attr.editTextStyle
) : MaterialAutoCompleteTextView(context, attrs, defStyleRes) {

    internal var filter = DEFAULT_FILTER
    internal var listItem = DEFAULT_LIST_ITEM
    internal var isEditable = DEFAULT_IS_EDITABLE

    private var onCutAction: ((Editable?) -> Unit)? = null
    private var onCopyAction: ((Editable?) -> Unit)? = null
    private var onPasteAction: ((Editable?) -> Unit)? = null

    private var onFilterAction: ((CharSequence?) -> Unit)? = null

    internal var adapter: ArrayAdapter<T>? = null
        set(value) {
            field = value?.apply {
                setAdapter(this)
            }
        }

    internal var array: Array<T>? = null
        set(value) {
            field = value?.apply {
                adapter = ArrayAdapter(context, listItem, this)
            }
        }

    override fun setText(text: CharSequence?, filter: Boolean) {
        super.setText(text, this.filter)
    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        when (filter) {
            true -> super.performFiltering(text, keyCode)
            false -> showDropDown()
        }
        onFilterAction?.invoke(text)
    }

    override fun showDropDown() {
        if (isEditable) super.showDropDown()
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

    internal fun setOnCutListener(action: (Editable?) -> Unit) {
        onCutAction = action
    }

    internal fun setOnCopyListener(action: (Editable?) -> Unit) {
        onCopyAction = action
    }

    internal fun setOnPasteListener(action: (Editable?) -> Unit) {
        onPasteAction = action
    }

    internal fun setOnFilterListener(action: (CharSequence?) -> Unit) {
        onFilterAction = action
    }

    companion object {
        const val DEFAULT_FILTER = false
        const val DEFAULT_LIST_ITEM = android.R.layout.simple_list_item_1
    }

}