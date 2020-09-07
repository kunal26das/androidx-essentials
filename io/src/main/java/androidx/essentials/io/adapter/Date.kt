package androidx.essentials.io.adapter

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.Date

private var fromUser = false

@BindingAdapter("date")
fun setDate(view: Date, date: Long?) {
    when (fromUser) {
        true -> fromUser = false
        false -> view.date = date
    }
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
        fromUser = true
        inverseBindingListener.onChange()
    }
}