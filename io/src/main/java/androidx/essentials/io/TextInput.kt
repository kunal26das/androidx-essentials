package androidx.essentials.io

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextInput : TextInputLayout {

    private val keyListener: KeyListener?
    private var attrs: AttributeSet? = null

    constructor(
        context: Context,
        attrs: AttributeSet? = null
    ) : super(context, attrs) {
        this.attrs = attrs
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.attrs = attrs
    }

    var isEditable: Boolean
        set(value) {
            field = value
            editText?.keyListener = when (value) {
                true -> keyListener
                false -> null
            }
        }
    private val inputMethodManager = InputMethodManager.getInstance(context)

    init {
        addView(TextInputEditText(context))
        keyListener = editText?.keyListener
        context.obtainStyledAttributes(attrs, R.styleable.TextInput, 0, 0).apply {
            isEditable = getBoolean(R.styleable.TextInput_editable, DEFAULT_IS_EDITABLE)
            recycle()
        }
        editText?.setOnFocusChangeListener { view, itHasFocus ->
            if (!isEditable && itHasFocus) {
                hideKeyboard(view)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val DEFAULT_IS_EDITABLE = false
    }
}