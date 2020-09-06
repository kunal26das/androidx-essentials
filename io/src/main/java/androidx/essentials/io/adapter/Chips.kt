package androidx.essentials.io.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.essentials.io.Chips

@BindingAdapter("selection")
fun setSelection(chips: Chips, selection: Array<String>) {
    chips.selection = selection.toCollection(ArrayList())
}

@InverseBindingAdapter(attribute = "selection")
fun getSelection(chips: Chips): Array<String> {
    return chips.selection.toTypedArray()
}

@BindingAdapter(value = ["selectionAttrChanged"])
fun setOnSelectionAttrChangeListener(
    chips: Chips,
    inverseBindingListener: InverseBindingListener
) {
    chips.setOnChipClickListener { _, _, _ ->
        inverseBindingListener.onChange()
    }
}