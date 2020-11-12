package androidx.essentials.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class EmptyStateAdapter(
    private val layout: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: ViewDataBinding

    override fun getItemCount() = 1

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            try {
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    layout, parent, false
                )
                binding.root
            } catch (e: NullPointerException) {
                LayoutInflater.from(parent.context).inflate(
                    layout, parent, false
                )
            }
        ) {}
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) = Unit

}
