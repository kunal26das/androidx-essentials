package androidx.essentials.io

import android.content.Context
import android.text.Editable
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
    protected lateinit var keyListener: KeyListener
    private val inputMethodManager = InputMethodManager.getInstance(context)

    var imeOptions = DEFAULT_IME_OPTIONS
        set(value) {
            field = value
            editText?.imeOptions = value
        }

    open var isEditable = DEFAULT_IS_EDITABLE
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
            if (isEditable and textChanged) {
                isValid
            }
        }


    var lines = DEFAULT_LINES
        set(value) {
            field = value
            editText?.setLines(value)
        }

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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText?.apply {
            doAfterTextChanged {
                textChanged = true
                isValid
            }
            setOnFocusChangeListener { view, itHasFocus ->
                if (!isEditable and itHasFocus) {
                    view.clearFocus()
                    hideKeyboard(view)
                } else if (!itHasFocus) {
                    hideKeyboard(view)
                }
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

    internal fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val DEFAULT_LINES = 1
        const val DEFAULT_IS_EDITABLE = true
        const val DEFAULT_IS_MANDATORY = false
        const val MESSAGE_REGEX = "Invalid Input"
        const val MESSAGE_MANDATORY = "Mandatory Field"
        const val DEFAULT_IME_OPTIONS = EditorInfo.IME_ACTION_NEXT
    }
}