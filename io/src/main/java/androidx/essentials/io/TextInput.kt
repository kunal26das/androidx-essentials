package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater

class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    var regex: Regex? = null
        set(value) {
            field = value
            isValid
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
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            isMandatory = getBoolean(R.styleable.TextInput_mandatory, DEFAULT_IS_MANDATORY)
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
}