package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.AutoCompleteTextView.Companion.DEFAULT_FILTER
import androidx.essentials.io.AutoCompleteTextView.Companion.DEFAULT_LIST_ITEM

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    private var onItemClickListener: OnItemClickListener? = null
    internal val editText by lazy { getEditText() as AutoCompleteTextView }

    var array: Array<String>
        get() = editText.array
        set(value) {
            editText.array = value
            if (isEditable and textChanged) isValid
        }

    override val isValid: Boolean
        get() {
            val text = editText.text?.toString() ?: ""
            isErrorEnabled = when {
                isMandatory and isEditable and text.isBlank() -> {
                    error = mandatoryMessage
                    true
                }
                isMandatory and !isEditable -> {
                    editText.array.isNotEmpty() and !editText.array.contains(text)
                }
                else -> false
            }
            return isVisible and !isErrorEnabled
        }

    init {
        with(AutoCompleteTextView(context, attrs)) {
            endIconMode = END_ICON_DROPDOWN_MENU
            mKeyListener = keyListener
            addView(this)
            hint = when {
                isHintEnabled -> ""
                else -> mHint
            }
        }
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            editText.listItem = getResourceId(R.styleable.AutoComplete_listItem, DEFAULT_LIST_ITEM)
            editText.filter = getBoolean(R.styleable.AutoComplete_android_filter, DEFAULT_FILTER)
            isMandatory = getBoolean(R.styleable.AutoComplete_mandatory, DEFAULT_IS_MANDATORY)
            isEditable = getBoolean(R.styleable.AutoComplete_editable, DEFAULT_IS_EDITABLE)
            validate = getBoolean(R.styleable.AutoComplete_validate, DEFAULT_VALIDATE)
            mandatoryMessage = getString(R.styleable.AutoComplete_mandatoryMessage)
            editText.array = try {
                context.resources.getStringArray(getResourceIdOrThrow(R.styleable.AutoComplete_array))
            } catch (e: IllegalArgumentException) {
                emptyArray()
            }
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText.apply {
            setOnFocusChangeListener { view, itHasFocus ->
                if (!isEditable && itHasFocus) {
                    editText.showDropDown()
                } else if (!itHasFocus) {
                    editText.dismissDropDown()
                    hideSoftInput(view)
                }
            }
            setOnItemClickListener { _, _, i, _ ->
                editText.clearFocus()
                this@AutoComplete.onItemClickListener?.onItemClick(i, array[i])
            }
        }
    }

    fun showDropDown() = editText.showDropDown()
    fun dismissDropDown() = editText.dismissDropDown()

    fun setOnCutListener(action: (Editable?) -> Unit) = editText.setOnCutListener(action)
    fun setOnCopyListener(action: (Editable?) -> Unit) = editText.setOnCopyListener(action)
    fun setOnPasteListener(action: (Editable?) -> Unit) = editText.setOnPasteListener(action)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: (index: Int, item: String) -> Unit) {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(index: Int, item: String) {
                onItemClickListener(index, item)
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClick(index: Int, item: String)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("text")
        fun AutoComplete.setText(text: String?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.text = text
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun AutoComplete.getText() = text

        @JvmStatic
        @BindingAdapter(value = ["textAttrChanged"])
        fun AutoComplete.setOnTextAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            editText.doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }
}