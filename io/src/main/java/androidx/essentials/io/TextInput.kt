package androidx.essentials.io

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInputLayout(context, attrs, defStyleAttr) {

    var regex: Regex? = null
    private lateinit var mHint: String
    private val keyListener: KeyListener
    private val inputMethodManager = InputMethodManager.getInstance(context)

    var isEditable = DEFAULT_IS_EDITABLE
        set(value) {
            field = value
            isMandatory = isMandatory
            editText?.keyListener = when (value) {
                true -> keyListener
                false -> null
            }
        }

    var isMandatory = DEFAULT_IS_MANDATORY
        set(value) {
            field = value
            hint = mHint
            isValid
        }

    val isValid: Boolean
        get() {
            val text = editText?.text?.toString() ?: ""
            isErrorEnabled = isEditable and when {
                isMandatory and text.isBlank() -> {
                    error = MESSAGE_MANDATORY
                    true
                }
                regex != null && !text.matches(regex!!) -> {
                    error = MESSAGE_REGEX
                    true
                }
                else -> false
            }
            return !isErrorEnabled
        }

    init {
        LayoutInflater.from(context).inflate(
            when (boxBackgroundMode) {
                BOX_BACKGROUND_FILLED -> R.layout.layout_text_input_edit_text_filled
                BOX_BACKGROUND_OUTLINE -> R.layout.layout_text_input_edit_text_outline
                else -> R.layout.layout_text_input_edit_text
            }, this, true
        )
        keyListener = editText?.keyListener!!
        context.obtainStyledAttributes(attrs, R.styleable.TextInput, defStyleAttr, 0).apply {
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            isMandatory = getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
            val pattern = getString(R.styleable.TextInput_regex)
            if (pattern != null) regex = Regex(pattern)
            initEditText()
            recycle()
        }
    }

    private fun initEditText() {
        editText?.apply {
            setLines(1)
            doAfterTextChanged { isValid }
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setOnFocusChangeListener { view, itHasFocus ->
                if (!isEditable && itHasFocus) {
                    hideKeyboard(view)
                    view.clearFocus()
                } else if (!itHasFocus) {
                    hideKeyboard(view)
                }
            }
        }
    }

    override fun setHint(hint: CharSequence?) {
        mHint = hint.toString()
        super.setHint(
            when (isEditable and isMandatory) {
                true -> "$mHint*"
                else -> mHint
            }
        )
    }

    private fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val DEFAULT_IS_EDITABLE = true
        private const val DEFAULT_IS_MANDATORY = false
        private const val MESSAGE_REGEX = "Invalid Input"
        private const val MESSAGE_MANDATORY = "Mandatory Field"
    }
}