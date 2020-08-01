package androidx.essentials.io

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter("text")
fun setText(textInput: TextInput, text: String?) {
    textInput.editText?.setText(text)
}

@InverseBindingAdapter(attribute = "text")
fun getText(textInput: TextInput): String? {
    return "${textInput.editText?.text}"
}

@BindingAdapter(value = ["textAttrChanged"])
fun setOnFieldInputChangeListener(
    textInput: TextInput,
    inverseBindingListener: InverseBindingListener
) {
    textInput.editText?.doAfterTextChanged {
        inverseBindingListener.onChange()
    }
}