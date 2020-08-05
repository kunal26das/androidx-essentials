package androidx.essentials.io

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInputLayout(context, attrs, defStyleAttr) {

    private lateinit var mHint: String
    private val keyListener: KeyListener
    private val listItem = android.R.layout.simple_list_item_1
    private val autoCompleteTextView: MaterialAutoCompleteTextView
    private val inputMethodManager = InputMethodManager.getInstance(context)

    var array = emptyArray<String>()
        set(value) {
            field = value
            autoCompleteTextView.setAdapter(ArrayAdapter(context, listItem, array))
        }

    var isEditable = DEFAULT_IS_EDITABLE
        set(value) {
            field = value
            editText?.keyListener = when (value) {
                true -> keyListener
                false -> null
            }
            isValid
        }

    var isMandatory = DEFAULT_IS_MANDATORY
        set(value) {
            field = value
            hint = mHint
            isValid
        }

    val isValid: Boolean
        get() {
            val text = editText?.text?.toString() ?: ""
            isErrorEnabled = when {
                isMandatory and text.isBlank() -> {
                    error = MESSAGE_MANDATORY
                    true
                }
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
        keyListener = editText?.keyListener!!
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            isEditable = getBoolean(R.styleable.AutoComplete_editable, DEFAULT_IS_EDITABLE)
            isMandatory = getBoolean(R.styleable.AutoComplete_mandatory, DEFAULT_IS_MANDATORY)
            array = try {
                context.resources.getStringArray(getResourceIdOrThrow(R.styleable.AutoComplete_array))
            } catch (e: IllegalArgumentException) {
                emptyArray()
            }
            initAutoCompleteTextView()
            initEditText()
            recycle()
        }
    }

    private fun initAutoCompleteTextView() {
        autoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            autoCompleteTextView.clearFocus()
        }
    }

    private fun initEditText() {
        editText?.apply {
            setLines(1)
            doAfterTextChanged { isValid }
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setOnFocusChangeListener { view, itHasFocus ->
                if (!isEditable && itHasFocus) {
                    view.clearFocus()
                    hideKeyboard(view)
                    autoCompleteTextView.showDropDown()
                } else if (!itHasFocus) {
                    hideKeyboard(view)
                    autoCompleteTextView.dismissDropDown()
                }
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

    private fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val DEFAULT_IS_EDITABLE = true
        private const val DEFAULT_IS_MANDATORY = false
        private const val MESSAGE_MANDATORY = "Mandatory Field"
    }
}