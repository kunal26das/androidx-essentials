package androidx.essentials.io.adapter

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.AutoComplete

@BindingAdapter("text")
fun setText(autoComplete: AutoComplete, text: String?) {
    autoComplete.editText?.setText(text)
}

@InverseBindingAdapter(attribute = "text")
fun getText(autoComplete: AutoComplete): String? {
    return "${autoComplete.editText?.text}"
}

@BindingAdapter(value = ["textAttrChanged"])
fun setOnTextAttrChangeListener(
    autoComplete: AutoComplete,
    inverseBindingListener: InverseBindingListener
) {
    autoComplete.editText?.doAfterTextChanged {
        inverseBindingListener.onChange()
    }
}