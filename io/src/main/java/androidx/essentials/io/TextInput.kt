package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.internal.EditText
import androidx.essentials.io.internal.Field
import java.util.*

open class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    private var regex: Regex? = null
    protected val locale: Locale get() = Locale.getDefault()
    internal val editText by lazy { getEditText() as EditText }

    override val isValid: Boolean
        get() {
            isErrorEnabled = isEditable and when {
                isMandatory and text.isNullOrBlank() -> {
                    error = mandatoryMessage
                    true
                }
                regex != null && text?.matches(regex!!) == false -> {
                    error = regexMessage
                    true
                }
                else -> false
            }
            return isVisible and !isErrorEnabled
        }

    init {
        with(EditText(context, attrs)) {
            if (isHintEnabled) hint = null
            addView(this)
        }
        context.obtainStyledAttributes(attrs, R.styleable.TextInput, defStyleAttr, 0).apply {
            isMandatory = getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            mandatoryMessage = getString(R.styleable.TextInput_mandatoryMessage)
                ?: context.getString(R.string.mandatory_field)
            regex = getString(R.styleable.TextInput_regex)?.let { Regex(it) }
            regexMessage = getString(R.styleable.TextInput_regexMessage)
                ?: context.getString(R.string.invalid_input)
            showSoftInputOnFocus = isEditable
            recycle()
        }
    }

    override fun show() {
        if (isEditable) {
            showSoftInput()
        } else hide()
    }

    override fun hide() = hideSoftInput()

    fun setOnCutListener(action: (Editable?) -> Unit) = editText.setOnCutListener(action)
    fun setOnCopyListener(action: (Editable?) -> Unit) = editText.setOnCopyListener(action)
    fun setOnPasteListener(action: (Editable?) -> Unit) = editText.setOnPasteListener(action)

    companion object {

        @JvmStatic
        @BindingAdapter("text")
        fun TextInput.setString(text: Any?) {
            when (fromUser) {
                true -> fromUser = false
                false -> text?.let {
                    this.text = "$it"
                }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun TextInput.getString() = text

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun TextInput.getInt() = text?.toIntOrNull()

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun TextInput.getLong() = text?.toLongOrNull()

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun TextInput.getFloat() = text?.toFloatOrNull()

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