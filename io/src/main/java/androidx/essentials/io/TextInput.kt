package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

open class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    internal val editText by lazy { getEditText() as EditText }

    open var regex: Regex? = null
        set(value) {
            field = value
            if (isEditable and textChanged) isValid
        }

    override val isValid: Boolean
        get() {
            val text = editText.text?.toString() ?: ""
            isErrorEnabled = isEditable and when {
                isMandatory and text.isBlank() -> {
                    error = mandatoryMessage
                    true
                }
                regex != null && !text.matches(regex!!) -> {
                    error = regexMessage
                    true
                }
                else -> false
            }
            return isVisible and !isErrorEnabled
        }

    init {
        with(EditText(context, attrs)) {
            mKeyListener = keyListener
            addView(this)
            hint = ""
        }
        context.obtainStyledAttributes(attrs, R.styleable.TextInput, defStyleAttr, 0).apply {
            isMandatory = getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            validate = getBoolean(R.styleable.TextInput_validate, DEFAULT_VALIDATE)
            mandatoryMessage = getString(R.styleable.TextInput_mandatoryMessage)
            regex = getString(R.styleable.TextInput_regex)?.let { Regex(it) }
            regexMessage = getString(R.styleable.TextInput_regexMessage)
            recycle()
        }
    }

    override fun setHint(hint: CharSequence?) {
        mHint = hint?.toString() ?: ""
        super.setHint(
            when (isEditable and isMandatory) {
                true -> if (mHint.isNotEmpty()) {
                    "$mHint*"
                } else mHint
                else -> mHint
            }.apply {
                if (!isHintEnabled) {
                    editText.hint = this
                }
            }
        )
    }

    fun setOnCutListener(action: (Editable?) -> Unit) = editText.setOnCutListener(action)
    fun setOnCopyListener(action: (Editable?) -> Unit) = editText.setOnCopyListener(action)
    fun setOnPasteListener(action: (Editable?) -> Unit) = editText.setOnPasteListener(action)

    companion object {

        @JvmStatic
        @BindingAdapter("text")
        fun TextInput.setText(text: String?) {
            when (fromUser) {
                true -> fromUser = false
                false -> text?.let { editText.setText(it) }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun TextInput.getText(): String? {
            with(editText.text) {
                return when {
                    isNullOrBlank() -> null
                    else -> toString()
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["textAttrChanged"])
        fun TextInput.setOnTextAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            editText.doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}