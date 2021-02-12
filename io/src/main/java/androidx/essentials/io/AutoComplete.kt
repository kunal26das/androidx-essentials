package androidx.essentials.io

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    private var listItem = DEFAULT_LIST_ITEM
    private var onItemClickListener: OnItemClickListener? = null
    private val editText by lazy { getEditText() as AutoCompleteTextView }

    private var adapter = ArrayAdapter<String>(context, listItem, emptyList())
        set(value) {
            field = value
            editText.apply {
                setAdapter(value)
                if (textChanged) showDropDown()
            }
        }

    var array = emptyArray<String>()
        set(value) {
            if (!field.contentEquals(value) or !filter) {
                field = value
                adapter = ArrayAdapter(context, listItem, value)
                if (isEditable and textChanged) isValid
            }
        }

    var filter = DEFAULT_FILTER

    override val isValid: Boolean
        get() {
            val text = editText.text?.toString() ?: ""
            isErrorEnabled = when {
                isMandatory and isEditable and text.isBlank() -> {
                    error = mandatoryMessage
                    true
                }
                isMandatory and !isEditable -> array.isNotEmpty() and !array.contains(text)
                else -> false
            }
            return isVisible and !isErrorEnabled
        }

    init {
        with(AutoCompleteTextView(context, attrs)) {
            endIconMode = END_ICON_DROPDOWN_MENU
            mKeyListener = keyListener
            addView(this)
            hint = ""
        }
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            isMandatory = getBoolean(R.styleable.AutoComplete_mandatory, DEFAULT_IS_MANDATORY)
            isEditable = getBoolean(R.styleable.AutoComplete_editable, DEFAULT_IS_EDITABLE)
            listItem = getResourceId(R.styleable.AutoComplete_listItem, DEFAULT_LIST_ITEM)
            filter = getBoolean(R.styleable.AutoComplete_android_filter, DEFAULT_FILTER)
            validate = getBoolean(R.styleable.AutoComplete_validate, DEFAULT_VALIDATE)
            mandatoryMessage = getString(R.styleable.AutoComplete_mandatoryMessage)
            array = try {
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
            doAfterTextChanged { array = array }
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

    fun setOnCutListener(action: (Editable?) -> Unit) {
        editText.setOnCutListener(action)
    }

    fun setOnCopyListener(action: (Editable?) -> Unit) {
        editText.setOnCopyListener(action)
    }

    fun setOnPasteListener(action: (Editable?) -> Unit) {
        editText.setOnPasteListener(action)
    }

    fun showDropDown() = editText.showDropDown()
    fun dismissDropDown() = editText.dismissDropDown()

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

        const val DEFAULT_FILTER = false
        const val DEFAULT_IS_EDITABLE = false
        const val DEFAULT_LIST_ITEM = android.R.layout.simple_list_item_1

        @JvmStatic
        @BindingAdapter("text")
        fun AutoComplete.setText(text: String?) {
            when (fromUser) {
                true -> fromUser = false
                false -> text?.let { editText.setText(it, filter) }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun AutoComplete.getText(): String? {
            with(editText.text) {
                return when (this) {
                    null -> null
                    else -> toString()
                }
            }
        }

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