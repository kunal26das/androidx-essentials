package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    private val listItem = android.R.layout.simple_list_item_1
    private var onItemClickListener: OnItemClickListener? = null
    private val autoCompleteTextView: MaterialAutoCompleteTextView

    private var adapter = ArrayAdapter<String>(context, listItem, emptyList())
        set(value) {
            field = value
            autoCompleteTextView.setAdapter(value)
            if (textChanged) {
                autoCompleteTextView.showDropDown()
            }
        }

    var array = emptyArray<String>()
        set(value) {
            if (!field.contentEquals(value)) {
                field = value
                adapter = ArrayAdapter(context, listItem, value)
                if (isEditable and textChanged) {
                    isValid
                }
            }
        }

    override val isValid: Boolean
        get() {
            val text = editText?.text?.toString() ?: ""
            isErrorEnabled = when {
                isMandatory and isEditable and text.isBlank() -> {
                    error = mandatoryMessage
                    true
                }
                isMandatory and !isEditable -> array.isNotEmpty() and !array.contains(text)
                else -> false
            }
            return !isErrorEnabled
        }

    init {
        LayoutInflater.from(context).inflate(
            when (boxBackgroundMode) {
                BOX_BACKGROUND_FILLED -> R.layout.layout_auto_complete_text_view_filled
                BOX_BACKGROUND_OUTLINE -> R.layout.layout_auto_complete_text_view_outlined
                else -> R.layout.layout_auto_complete_text_view
            }, this, true
        )
        mKeyListener = editText?.keyListener!!
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            lines = getInt(R.styleable.AutoComplete_android_lines, DEFAULT_LINES)
            validate = getBoolean(R.styleable.AutoComplete_validate, DEFAULT_VALIDATE)
            maxLines = getInt(R.styleable.AutoComplete_android_maxLines, DEFAULT_LINES)
            minLines = getInt(R.styleable.AutoComplete_android_minLines, DEFAULT_LINES)
            isEditable = getBoolean(R.styleable.AutoComplete_editable, DEFAULT_IS_EDITABLE)
            isMandatory = getBoolean(R.styleable.AutoComplete_mandatory, DEFAULT_IS_MANDATORY)
            inputType = getInt(R.styleable.AutoComplete_android_inputType, DEFAULT_INPUT_TYPE)
            imeOptions = getInt(R.styleable.AutoComplete_android_imeOptions, DEFAULT_IME_OPTIONS)
            mandatoryMessage = getString(R.styleable.AutoComplete_mandatoryMessage)
                ?: context.getString(R.string.mandatory_field)
            array = try {
                context.resources.getStringArray(getResourceIdOrThrow(R.styleable.AutoComplete_array))
            } catch (e: IllegalArgumentException) {
                emptyArray()
            }
            try {
                getResourceIdOrThrow(R.styleable.AutoComplete_android_fontFamily).let {
                    typeface = ResourcesCompat.getFont(context, it)
                }
            } catch (e: IllegalArgumentException) {
            }
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        autoCompleteTextView.apply {
            setOnFocusChangeListener { view, itHasFocus ->
                if (!isEditable && itHasFocus) {
                    autoCompleteTextView.showDropDown()
                } else if (!itHasFocus) {
                    autoCompleteTextView.dismissDropDown()
                    hideSoftInput(view)
                }
            }
            setOnItemClickListener { _, _, i, _ ->
                autoCompleteTextView.clearFocus()
                this@AutoComplete.onItemClickListener?.onItemClick(i, array[i])
            }
        }
    }

    override fun setHint(hint: CharSequence?) {
        mHint = hint.toString()
        super.setHint(
            when (isMandatory) {
                true -> "$mHint*"
                else -> mHint
            }
        )
    }

    fun showDropDown() = autoCompleteTextView.showDropDown()
    fun dismissDropDown() = autoCompleteTextView.dismissDropDown()

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: (index: Int, item: String) -> Unit) {
        this.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(index: Int, item: String) {
                onItemClickListener(index, item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(index: Int, item: String)
    }

    companion object {
        const val DEFAULT_IS_EDITABLE = false
    }
}