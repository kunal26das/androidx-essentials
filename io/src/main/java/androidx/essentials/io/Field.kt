package androidx.essentials.io

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.essentials.io.input.InputMethodManager
import com.google.android.material.textfield.TextInputLayout

abstract class Field @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInputLayout(context, attrs, defStyleAttr) {

    abstract val isValid: Boolean
    protected var textChanged = false
    protected lateinit var mHint: String
    val isInvalid get() = !isValid
    protected lateinit var mKeyListener: KeyListener
    private val inputMethodManager = InputMethodManager.getInstance(context)

    var imeOptions = DEFAULT_IME_OPTIONS
        set(value) {
            field = value
            editText?.imeOptions = value
        }

    open var inputType = DEFAULT_INPUT_TYPE
        set(value) {
            field = value
            editText?.inputType = value
        }

    open var isEditable = DEFAULT_IS_EDITABLE
        set(value) {
            field = value
            isMandatory = isMandatory
            if (!value) {
                editText?.clearFocus()
                isErrorEnabled = false
            }
            editText?.apply {
                isFocusable = value
                isCursorVisible = value
                isFocusableInTouchMode = value
                keyListener = when (value) {
                    true -> mKeyListener
                    false -> null
                }
            }
        }

    var isMandatory = DEFAULT_IS_MANDATORY
        set(value) {
            field = value
            hint = mHint
            if (isEditable and textChanged) {
                isValid
            }
        }

    var lines = DEFAULT_LINES
        set(value) {
            field = value
            editText?.setLines(value)
        }

    var mandatoryMessage = context.getString(R.string.mandatory_field)

    var maxLines = DEFAULT_LINES
        set(value) {
            field = value
            editText?.maxLines = value
        }

    var minLines = DEFAULT_LINES
        set(value) {
            field = value
            editText?.minLines = value
        }

    var regexMessage = context.getString(R.string.invalid_input)

    var mTypeFace = Typeface.NORMAL
        set(value) {
            field = value
            editText?.setTypeface(
                editText?.typeface,
                when (value) {
                    Typeface.BOLD -> Typeface.BOLD
                    Typeface.ITALIC -> Typeface.ITALIC
                    Typeface.BOLD_ITALIC -> Typeface.BOLD_ITALIC
                    else -> Typeface.NORMAL
                }
            )
        }

    var validate = DEFAULT_VALIDATE

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText?.apply {
            doAfterTextChanged {
                textChanged = true
                if (validate) isValid
            }
            setOnFocusChangeListener { view, itHasFocus ->
                when (isEditable and itHasFocus) {
                    true -> post {
                        showSoftInput(view)
                        setSelection(length())
                    }
                    false -> post {
                        hideSoftInput(view)
                    }
                }
            }
        }
        setOnFocusChangeListener { _, itHasFocus ->
            when (isEditable and itHasFocus) {
                true -> editText?.requestFocus()
                false -> editText?.clearFocus()
            }
        }
    }

    fun doBeforeTextChanged(
        action: (
            text: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) -> Unit
    ) = editText?.addTextChangedListener(beforeTextChanged = action)

    fun doOnTextChanged(
        action: (
            text: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) -> Unit
    ) = editText?.addTextChangedListener(onTextChanged = action)

    fun doAfterTextChanged(
        action: (text: Editable?) -> Unit
    ) = editText?.addTextChangedListener(afterTextChanged = action)

    internal fun hideSoftInput(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    internal fun showSoftInput(view: View) {
        inputMethodManager.showSoftInput(view, 0)
    }

    companion object {
        const val DEFAULT_LINES = 1
        const val DEFAULT_VALIDATE = false
        const val DEFAULT_IS_EDITABLE = true
        const val DEFAULT_IS_MANDATORY = false
        const val DEFAULT_TYPEFACE = Typeface.NORMAL
        const val DEFAULT_INPUT_TYPE = InputType.TYPE_CLASS_TEXT
        const val DEFAULT_IME_OPTIONS = EditorInfo.IME_ACTION_NEXT
    }
}