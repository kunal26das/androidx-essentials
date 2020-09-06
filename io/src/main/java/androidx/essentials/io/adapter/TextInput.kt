package androidx.essentials.io.adapter

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.TextInput

@BindingAdapter("text")
fun setText(textInput: TextInput, text: String?) {
    textInput.editText?.setText(text)
}

@InverseBindingAdapter(attribute = "text")
fun getText(textInput: TextInput): String? {
    return "${textInput.editText?.text}"
}

@BindingAdapter(value = ["textAttrChanged"])
fun setOnTextAttrChangeListener(
    textInput: TextInput,
    inverseBindingListener: InverseBindingListener
) {
    textInput.editText?.doAfterTextChanged {
        inverseBindingListener.onChange()
    }
}