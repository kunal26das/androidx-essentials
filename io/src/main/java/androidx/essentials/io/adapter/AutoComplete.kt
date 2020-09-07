package androidx.essentials.io.adapter

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.AutoComplete

private var fromUser = false

@BindingAdapter("text")
fun setText(autoComplete: AutoComplete, text: String?) {
    when (fromUser) {
        true -> fromUser = false
        false -> text?.let { autoComplete.editText?.setText(it) }
    }
}

@InverseBindingAdapter(attribute = "text")
fun getText(autoComplete: AutoComplete): String? {
    return when (autoComplete.editText?.text) {
        null -> null
        else -> "${autoComplete.editText?.text}"
    }
}

@BindingAdapter(value = ["textAttrChanged"])
fun setOnTextAttrChangeListener(
    autoComplete: AutoComplete,
    inverseBindingListener: InverseBindingListener
) {
    autoComplete.editText?.doAfterTextChanged {
        fromUser = true
        inverseBindingListener.onChange()
    }
}