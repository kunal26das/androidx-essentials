package androidx.essentials.io

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

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

@BindingAdapter("date")
fun setDate(view: Date, date: Long?) {
    view.date = date
}

@InverseBindingAdapter(attribute = "date")
fun getDate(view: Date): Long? {
    return view.date
}

@BindingAdapter(value = ["dateAttrChanged"])
fun setOnDateAttrChangeListener(
    view: Date,
    inverseBindingListener: InverseBindingListener
) {
    view.editText?.doAfterTextChanged {
        inverseBindingListener.onChange()
    }
}