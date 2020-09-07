package androidx.essentials.io.adapter

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.TextInput

private var fromUser = false

@BindingAdapter("text")
fun setText(textInput: TextInput, text: String?) {
    when (fromUser) {
        true -> fromUser = false
        false -> text?.let { textInput.editText?.setText(it) }
    }
}

@InverseBindingAdapter(attribute = "text")
fun getText(textInput: TextInput): String? {
    return when (textInput.editText?.text) {
        null -> null
        else -> "${textInput.editText?.text}"
    }
}

@BindingAdapter(value = ["textAttrChanged"])
fun setOnTextAttrChangeListener(
    textInput: TextInput,
    inverseBindingListener: InverseBindingListener
) {
    textInput.editText?.doAfterTextChanged {
        fromUser = true
        inverseBindingListener.onChange()
    }
}