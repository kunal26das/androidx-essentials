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
import androidx.essentials.io.internal.AutoCompleteTextView
import androidx.essentials.io.internal.AutoCompleteTextView.Companion.DEFAULT_FILTER
import androidx.essentials.io.internal.AutoCompleteTextView.Companion.DEFAULT_LIST_ITEM
import androidx.essentials.io.internal.Field

class AutoComplete @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : Field(context, attrs, defStyleAttr) {

    private var onItemClickListener: OnItemClickListener? = null
    internal val editText by lazy { getEditText() as AutoCompleteTextView }

    var array: Array<String>?
        get() = editText.array
        set(value) {
            if (isEditable and fromUser) isValid
            editText.array = value ?: emptyArray()
        }

    override val isValid: Boolean
        get() {
            isErrorEnabled = isEditable and when {
                isMandatory and editText.text.isNullOrBlank() -> {
                    error = mandatoryMessage
                    true
                }
                isMandatory and !showSoftInputOnFocus -> {
                    editText.array.isNotEmpty() and !editText.array.contains("${editText.text}")
                }
                else -> false
            }
            return isVisible and !isErrorEnabled
        }

    init {
        with(AutoCompleteTextView(context, attrs)) {
            endIconMode = END_ICON_DROPDOWN_MENU
            if (isHintEnabled) hint = null
            addView(this)
        }
        context.obtainStyledAttributes(attrs, R.styleable.AutoComplete, defStyleAttr, 0).apply {
            editText.listItem = getResourceId(R.styleable.AutoComplete_listItem, DEFAULT_LIST_ITEM)
            editText.filter = getBoolean(R.styleable.AutoComplete_android_filter, DEFAULT_FILTER)
            isMandatory = getBoolean(R.styleable.AutoComplete_mandatory, DEFAULT_IS_MANDATORY)
            isEditable = getBoolean(R.styleable.AutoComplete_editable, DEFAULT_IS_EDITABLE)
            showSoftInputOnFocus =
                getBoolean(R.styleable.AutoComplete_showSoftInputOnFocus, isEditable)
            mandatoryMessage = getString(R.styleable.AutoComplete_mandatoryMessage)
                ?: context.getString(R.string.mandatory_field)
            editText.array = try {
                resources.getStringArray(getResourceIdOrThrow(R.styleable.AutoComplete_array))
            } catch (e: IllegalArgumentException) {
                emptyArray()
            }
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText.setOnItemClickListener { _, _, i, _ ->
            editText.clearFocus()
            this@AutoComplete.onItemClickListener?.onItemClick(i, array?.get(i))
        }
    }

    override fun show() {
        if (isEditable) {
            editText.showDropDown()
            showSoftInput()
        } else hide()
    }

    override fun hide() {
        editText.dismissDropDown()
        hideSoftInput()
    }

    fun setOnCutListener(action: (Editable?) -> Unit) = editText.setOnCutListener(action)
    fun setOnCopyListener(action: (Editable?) -> Unit) = editText.setOnCopyListener(action)
    fun setOnPasteListener(action: (Editable?) -> Unit) = editText.setOnPasteListener(action)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: (index: Int, item: String?) -> Unit) {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(index: Int, item: String?) {
                onItemClickListener(index, item)
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClick(index: Int, item: String?)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("text")
        fun AutoComplete.setString(text: String?) {
            when (fromUser) {
                true -> fromUser = false
                false -> editText.setText(
                    when {
                        text == null -> null
                        text.isEmpty() -> null
                        else -> text
                    }
                )
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun AutoComplete.getString(): String? {
            with(editText.text) {
                return when {
                    this == null -> null
                    isEmpty() -> null
                    else -> "$this"
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