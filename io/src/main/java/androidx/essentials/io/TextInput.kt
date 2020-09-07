package androidx.essentials.io

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow

open class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    var maxLength = DEFAULT_MAX_LENGTH
        set(value) {
            field = value
            editText?.filters?.toCollection(ArrayList())?.apply {
                add(InputFilter.LengthFilter(value))
                editText?.filters = toTypedArray()
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
            lines = getInt(R.styleable.TextInput_android_lines, DEFAULT_LINES)
            maxLines = getInt(R.styleable.TextInput_android_maxLines, DEFAULT_LINES)
            minLines = getInt(R.styleable.TextInput_android_minLines, DEFAULT_LINES)
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            isMandatory = getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
            inputType = getInt(R.styleable.TextInput_android_inputType, DEFAULT_INPUT_TYPE)
            maxLength = getInt(R.styleable.TextInput_android_maxLength, DEFAULT_MAX_LENGTH)
            imeOptions = getInt(R.styleable.TextInput_android_imeOptions, DEFAULT_IME_OPTIONS)
            try {
                getResourceIdOrThrow(R.styleable.TextInput_android_fontFamily).let {
                    typeface = ResourcesCompat.getFont(context, it)
                }
            } catch (e: IllegalArgumentException) {
            }
            val pattern = getString(R.styleable.TextInput_regex)
            if (pattern != null) regex = Regex(pattern)
            recycle()
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

    companion object {
        const val DEFAULT_MAX_LENGTH = Int.MAX_VALUE
    }

}