package androidx.essentials.io.generic

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.R
import androidx.essentials.io.internal.AutoCompleteTextView
import androidx.essentials.io.internal.AutoCompleteTextView.Companion.DEFAULT_FILTER
import androidx.essentials.io.internal.AutoCompleteTextView.Companion.DEFAULT_LIST_ITEM
import androidx.essentials.io.internal.Field

abstract class GenericAutoComplete<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    internal val editText: AutoCompleteTextView<T>
    private var onItemSelectedListener: OnItemSelectedListener<T>? = null

    var array: Array<T>? = null
        set(value) {
            field = value
            editText.array = value
            if (value?.contains(selection) == false) {
                selection = null
            }
        }

    var filter = DEFAULT_FILTER
        set(value) {
            field = value
            editText.filter = value
        }

    var selection: T? = null
        set(value) {
            if (field != value) {
                field = value
                onItemSelectedListener?.onItemSelected(value)
                if (value != null) {
                    editText.setText(value.toString())
                    clearFocus()
                    hide()
                }
            }
        }

    override var isEditable = DEFAULT_IS_EDITABLE
        set(value) {
            field = value
            super.isEditable = value
            isEndIconVisible = value
            editText.isEditable = value
        }

    override val isValid: Boolean
        get() {
            isErrorEnabled = isEditable and when {
                isMandatory and editText.text.isNullOrEmpty() -> {
                    error = mandatoryMessage
                    true
                }
                isMandatory and !showSoftInputOnFocus -> {
                    editText.array?.contains(selection) == false
                }
                else -> false
            }
            return isVisible and !isErrorEnabled
        }

    init {
        with(AutoCompleteTextView<T>(context, attrs)) {
            endIconMode = END_ICON_DROPDOWN_MENU
            if (isHintEnabled) hint = null
            editText = this
            addView(this)
        }
        context.obtainStyledAttributes(attrs, R.styleable.GenericAutoComplete, defStyleAttr, 0)
            .apply {
                editText.listItem =
                    getResourceId(R.styleable.GenericAutoComplete_listItem, DEFAULT_LIST_ITEM)
                filter =
                    getBoolean(R.styleable.GenericAutoComplete_android_filter, DEFAULT_FILTER)
                isMandatory =
                    getBoolean(R.styleable.GenericAutoComplete_mandatory, DEFAULT_IS_MANDATORY)
                isEditable =
                    getBoolean(R.styleable.GenericAutoComplete_editable, DEFAULT_IS_EDITABLE)
                showSoftInputOnFocus =
                    getBoolean(R.styleable.GenericAutoComplete_showSoftInputOnFocus, isEditable)
                mandatoryMessage = getString(R.styleable.GenericAutoComplete_mandatoryMessage)
                    ?: context.getString(R.string.mandatory_field)
                recycle()
            }
        editText.setOnItemClickListener { _, _, position, _ ->
            selection = editText.adapter?.getItem(position)
        }
        editText.setOnFilterListener {
            if ("$it" != selection?.toString()) {
                selection = null
            }
        }
    }

    override fun show() {
        if (isEditable) {
            editText.showDropDown()
            showSoftInput()
        } else hide()
    }

    override fun hide() {
        editText.dismissDropDown()
        hideSoftInput()
    }

    fun setOnCutListener(action: (Editable?) -> Unit) = editText.setOnCutListener(action)
    fun setOnCopyListener(action: (Editable?) -> Unit) = editText.setOnCopyListener(action)
    fun setOnPasteListener(action: (Editable?) -> Unit) = editText.setOnPasteListener(action)

    fun setOnItemSelectedListener(onItemClickListener: OnItemSelectedListener<T>) {
        this.onItemSelectedListener = onItemClickListener
    }

    fun setOnItemSelectedListener(onItemSelectedListener: (item: T?) -> Unit) {
        setOnItemSelectedListener(object : OnItemSelectedListener<T> {
            override fun onItemSelected(item: T?) {
                onItemSelectedListener(item)
            }
        })
    }

    interface OnItemSelectedListener<T> {
        fun onItemSelected(item: T?)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("array")
        fun <T> GenericAutoComplete<T>.setArray(array: Array<T>) {
            editText.array = array
        }

        @JvmStatic
        @BindingAdapter("selection")
        fun <T> GenericAutoComplete<T>.setSelection(selection: T?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.selection = selection
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "selection")
        fun <T> GenericAutoComplete<T>.getSelection() = selection

        @JvmStatic
        @BindingAdapter(value = ["selectionAttrChanged"])
        fun <T> GenericAutoComplete<T>.setOnSelectionAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            setOnItemSelectedListener {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }
}