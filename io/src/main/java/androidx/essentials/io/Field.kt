package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.essentials.io.input.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout

abstract class Field @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInputLayout(context, attrs, defStyleAttr) {

    abstract val isValid: Boolean
    val isInvalid get() = !isValid
    protected var fromUser = false
    protected var textChanged = false
    protected lateinit var mHint: String
    protected var mKeyListener: KeyListener? = null
    private val activity = context as AppCompatActivity
    private val inputMethodManager = InputMethodManager.getInstance(context)
    private val toast by lazy { Toast.makeText(context, "", Toast.LENGTH_SHORT) }

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

    var mandatoryMessage: String? = context.getString(R.string.mandatory_field)
        set(value) {
            if (value != null) field = value
        }

    var regexMessage: String? = context.getString(R.string.invalid_input)
        set(value) {
            if (value != null) field = value
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
        action: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit
    ) = editText?.addTextChangedListener(beforeTextChanged = action)

    fun doOnTextChanged(
        action: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit
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

    protected fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast.apply {
            setDuration(duration)
            setText(message)
        }.show()
    }

    protected fun DialogFragment.show() {
        if (!isAdded) this@Field.activity.supportFragmentManager.let { show(it, null) }
    }

    companion object {
        const val DEFAULT_VALIDATE = false
        const val DEFAULT_IS_EDITABLE = true
        const val DEFAULT_IS_MANDATORY = false
    }
}