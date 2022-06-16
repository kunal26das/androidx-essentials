package androidx.essentials.io.internal

import android.content.Context
import android.content.ContextWrapper
import android.text.Editable
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.R
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

    private val appCompatActivity: AppCompatActivity?
        get() {
            var context = context
            while (context is ContextWrapper) {
                if (context is AppCompatActivity) return context
                context = context.baseContext
            }
            return null
        }

    private val inputMethodManager = context.getSystemService(InputMethodManager::class.java)

    internal var inputType: Int
        get() = editText?.inputType ?: EditorInfo.TYPE_NULL
        set(value) {
            editText?.post {
                editText?.inputType = value
            }
        }

    open var isEditable = DEFAULT_IS_EDITABLE
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
            if (!hint.isNullOrEmpty()) {
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
        editText?.setOnFocusChangeListener { _, hasFocus ->
            when (isEditable and hasFocus) {
                true -> show()
                false -> hide()
            }
        }
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
        if (showSoftInputOnFocus) post {
            editText?.apply { setSelection(length()) }
            inputMethodManager.showSoftInput(editText, 0)
        }
    }

    @Synchronized
    protected fun DialogFragment.show() {
        if (!isAdded) appCompatActivity?.supportFragmentManager?.let {
            showNow(it, null)
        }
    }

    companion object {

        const val DEFAULT_IS_EDITABLE = true
        const val DEFAULT_IS_MANDATORY = false

        @JvmStatic
        @BindingAdapter("text")
        fun Field.setText(text: Any?) {
            when (fromUser) {
                true -> fromUser = false
                false -> with(text) {
                    editText?.setText(
                        when (this) {
                            is String -> when {
                                isNullOrEmpty() -> null
                                else -> "$this"
                            }
                            else -> when (this) {
                                null -> null
                                else -> "$this"
                            }
                        }
                    )
                }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun Field.getText(): String? {
            with(editText?.text) {
                return when {
                    isNullOrEmpty() -> null
                    else -> "$this"
                }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun Field.getInt() = getText()?.toIntOrNull()

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun Field.getLong() = getText()?.toLongOrNull()

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun Field.getFloat() = getText()?.toFloatOrNull()

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun Field.getDouble() = getText()?.toDoubleOrNull()

        @JvmStatic
        @BindingAdapter(value = ["textAttrChanged"])
        fun Field.setOnTextAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }
}