package androidx.essentials.io.internal

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.essentials.extensions.Context.getActivity
import androidx.essentials.io.*
import androidx.essentials.io.input.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout

abstract class Field @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInputLayout(context, attrs, defStyleAttr) {

    open val isValid = true
    val isInvalid get() = !isValid
    protected var fromUser = false

    private val activity get() = context.getActivity<AppCompatActivity>()
    private val onFocusChangeListeners = mutableListOf<(Boolean) -> Unit>()
    private val inputMethodManager = InputMethodManager.getInstance(context)

    internal var inputType: Int
        get() = editText?.inputType ?: EditorInfo.TYPE_NULL
        set(value) {
            editText?.post {
                editText?.inputType = value
            }
        }

    var isEditable = DEFAULT_IS_EDITABLE
        set(value) {
            field = value
            isFocusable = value
            isMandatory = isMandatory
            editText?.isFocusable = value
            isFocusableInTouchMode = value
            editText?.isFocusableInTouchMode = value
            showSoftInputOnFocus = showSoftInputOnFocus
        }

    var isMandatory = DEFAULT_IS_MANDATORY
        set(value) {
            field = value
            if (!hint.isNullOrBlank()) {
                when (value and isEditable) {
                    true -> if (hint?.last() != '*') {
                        hint = "$hint*"
                    }
                    false -> if (hint?.last() == '*') {
                        hint = hint?.dropLast(1)
                    }
                }
            }
        }

    var mandatoryMessage = context.getString(R.string.mandatory_field)

    var regexMessage = context.getString(R.string.invalid_input)

    var showSoftInputOnFocus
        get() = editText?.showSoftInputOnFocus ?: false
        set(value) {
            editText?.apply {
                showSoftInputOnFocus = value
                if (!value) clearFocus()
                isCursorVisible = value
            }
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText?.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) post {
                    setSelection(length())
                }
                when (isEditable and hasFocus) {
                    true -> show()
                    false -> hide()
                }
            }
        }
    }

    fun addOnFocusChangeListener(onFocusChange: (Boolean) -> Unit) {
        onFocusChangeListeners += onFocusChange
    }

    fun doBeforeTextChanged(
        action: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit
    ) = editText?.addTextChangedListener(beforeTextChanged = action)

    fun doOnTextChanged(
        action: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit
    ) = editText?.addTextChangedListener(onTextChanged = action)

    fun doAfterTextChanged(
        action: (text: Editable?) -> Unit
    ) = editText?.addTextChangedListener(afterTextChanged = action)

    abstract fun show()
    open fun hide() = Unit

    internal fun hideSoftInput() {
        inputMethodManager.hideSoftInputFromWindow(editText?.windowToken, 0)
    }

    internal fun showSoftInput() {
        if (showSoftInputOnFocus) inputMethodManager.showSoftInput(editText, 0)
    }

    protected fun DialogFragment.show() {
        if (!isAdded) this@Field.activity?.supportFragmentManager?.let { show(it, null) }
    }

    companion object {
        const val DEFAULT_IS_EDITABLE = true
        const val DEFAULT_IS_MANDATORY = false
    }
}