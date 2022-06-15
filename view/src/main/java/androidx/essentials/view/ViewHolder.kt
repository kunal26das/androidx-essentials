package androidx.essentials.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder<VDB : ViewDataBinding>(
    parent: ViewGroup, @LayoutRes layout: Int
) : RecyclerView.ViewHolder(
    DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(parent.context),
        layout, parent, false
    ).root
) {

    val binding = DataBindingUtil.getBinding<VDB>(itemView)

}