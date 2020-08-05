package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.core.content.res.getResourceIdOrThrow
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    private val listItem = android.R.layout.simple_list_item_1
    private val autoCompleteTextView: MaterialAutoCompleteTextView

    var array = emptyArray<String>()
        set(value) {
            field = value
            isValid
            autoCompleteTextView.setAdapter(ArrayAdapter(context, listItem, array))
        }

    override val isValid: Boolean
        get() {
            val text = editText?.text?.toString() ?: ""
            isErrorEnabled = when {
                isMandatory and text.isBlank() -> {
                    error = MESSAGE_MANDATORY
                    true
                }
                isEditable -> text.isNotBlank() and !array.contains(text)
                else -> false
            }
            return !isErrorEnabled
        }

    init {
        LayoutInflater.from(context).inflate(
            when (boxBackgroundMode) {
                BOX_BACKGROUND_FILLED -> R.layout.layout_auto_complete_text_view_filled
                BOX_BACKGROUND_OUTLINE -> R.layout.layout_auto_complete_text_view_outlined
                else -> R.layout.layout_auto_complete_text_view
            }, this, true
        )
        keyListener = editText?.keyListener!!
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            isEditable = getBoolean(R.styleable.AutoComplete_editable, DEFAULT_IS_EDITABLE)
            isMandatory = getBoolean(R.styleable.AutoComplete_mandatory, DEFAULT_IS_MANDATORY)
            array = try {
                context.resources.getStringArray(getResourceIdOrThrow(R.styleable.AutoComplete_array))
            } catch (e: IllegalArgumentException) {
                emptyArray()
            }
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        autoCompleteTextView.apply {
            setOnFocusChangeListener { _, itHasFocus ->
                if (!isEditable && itHasFocus) {
                    autoCompleteTextView.showDropDown()
                } else if (!itHasFocus) {
                    autoCompleteTextView.dismissDropDown()
                }
            }
            setOnItemClickListener { _, _, _, _ ->
                autoCompleteTextView.clearFocus()
            }
        }
    }

    override fun setHint(hint: CharSequence?) {
        mHint = hint.toString()
        super.setHint(
            when (isMandatory) {
                true -> "$mHint*"
                else -> mHint
            }
        )
    }
}