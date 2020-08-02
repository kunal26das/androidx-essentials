package androidx.essentials.io

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInputLayout(context, attrs, defStyleAttr) {

    var regex: Regex? = null
    private lateinit var mHint: String
    private val keyListener: KeyListener
    private val defaultBoxStrokeWidth: Int
    private val defaultBoxStrokeWidthFocused: Int
    private val inputMethodManager = InputMethodManager.getInstance(context)

    var isEditable = DEFAULT_IS_EDITABLE
        set(value) {
            field = value
            when (value) {
                true -> {
                    editText?.keyListener = keyListener
                    boxStrokeWidth = defaultBoxStrokeWidth
                    boxStrokeWidthFocused = defaultBoxStrokeWidthFocused
                }
                false -> {
                    boxStrokeWidth = 0
                    boxStrokeWidthFocused = 0
                    editText?.keyListener = null
                }
            }
        }

    var isMandatory = DEFAULT_IS_MANDATORY
        set(value) {
            field = value
            hint = mHint
        }

    val isValid: Boolean
        get() {
            val text = editText?.text?.toString() ?: ""
            isErrorEnabled = when {
                isMandatory and text.isBlank() -> {
                    error = MESSAGE_MANDATORY
                    true
                }
                isEditable && regex != null && !text.matches(regex!!) -> {
                    error = MESSAGE_REGEX
                    true
                }
                else -> false
            }
            return !isErrorEnabled
        }

    init {
        addView(TextInputEditText(context))
        keyListener = editText?.keyListener!!
        defaultBoxStrokeWidth = boxStrokeWidth
        defaultBoxStrokeWidthFocused = boxStrokeWidthFocused

        context.obtainStyledAttributes(attrs, R.styleable.TextInput, defStyleAttr, 0).apply {
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            isMandatory =
                isEditable and getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
            val pattern = getString(R.styleable.TextInput_regex)
            if (pattern != null) regex = Regex(pattern)
            recycle()
        }

        editText?.apply {
            setLines(1)
            doAfterTextChanged { isValid }
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setOnFocusChangeListener { view, itHasFocus ->
                if (!isEditable && itHasFocus) {
                    hideKeyboard(view)
                } else if (!itHasFocus) {
                    hideKeyboard(view)
                }
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