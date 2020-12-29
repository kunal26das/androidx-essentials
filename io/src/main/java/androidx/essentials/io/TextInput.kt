package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

open class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    var maxLength = DEFAULT_MAX_LENGTH
        set(value) {
            field = value
            editText.filters?.toCollection(ArrayList())?.apply {
                add(InputFilter.LengthFilter(value))
                editText.filters = toTypedArray()
            }
        }

    open var regex: Regex? = null
        set(value) {
            field = value
            if (isEditable and textChanged) {
                isValid
            }
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
        mKeyListener = editText.keyListener!!
        context.obtainStyledAttributes(attrs, R.styleable.TextInput, defStyleAttr, 0).apply {
            regex = getString(R.styleable.TextInput_regex)?.let { Regex(it) }
            lines = getInt(R.styleable.TextInput_android_lines, DEFAULT_LINES)
            validate = getBoolean(R.styleable.TextInput_validate, DEFAULT_VALIDATE)
            maxLines = getInt(R.styleable.TextInput_android_maxLines, DEFAULT_LINES)
            minLines = getInt(R.styleable.TextInput_android_minLines, DEFAULT_LINES)
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            inputType = getInt(R.styleable.TextInput_android_inputType, DEFAULT_INPUT_TYPE)
            maxLength = getInt(R.styleable.TextInput_android_maxLength, DEFAULT_MAX_LENGTH)
            isMandatory = getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
            imeOptions = getInt(R.styleable.TextInput_android_imeOptions, DEFAULT_IME_OPTIONS)
            regexMessage = getString(R.styleable.TextInput_regexMessage)
                ?: context.getString(R.string.invalid_input)
            mandatoryMessage = getString(R.styleable.TextInput_mandatoryMessage)
                ?: context.getString(R.string.mandatory_field)
            editText.apply {
                textSize = getDimensionPixelSize(
                    R.styleable.TextInput_android_textSize,
                    textSize.toInt()
                ) / resources.displayMetrics.scaledDensity
            }
            try {
                mTypeFace = getInt(R.styleable.TextInput_android_textStyle, DEFAULT_TYPEFACE)
                getResourceIdOrThrow(R.styleable.TextInput_android_fontFamily).let {
                    typeface = ResourcesCompat.getFont(context, it)
                }
            } catch (e: IllegalArgumentException) {
            }
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

    override fun getEditText(): EditText {
        return super.getEditText() as EditText
    }

    fun setOnCutListener(action: (Editable?) -> Unit) {
        editText.setOnCutListener(action)
    }

    fun setOnCopyListener(action: (Editable?) -> Unit) {
        editText.setOnCopyListener(action)
    }

    fun setOnPasteListener(action: (Editable?) -> Unit) {
        editText.setOnPasteListener(action)
    }

    companion object {

        const val DEFAULT_MAX_LENGTH = Int.MAX_VALUE

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
        fun getText(textInput: TextInput): String? {
            with(textInput.editText.text) {
                return when (this) {
                    null -> null
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