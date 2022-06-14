package androidx.essentials.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ListStateAdapter(
    private val layout: Int,
    private val parent: ViewGroup,
    private val attachToRoot: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var viewDataBinding: ViewDataBinding
    fun <T : ViewDataBinding> dataBinding(): T = viewDataBinding as T
    private val viewHolder = object : RecyclerView.ViewHolder(
        try {
            viewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layout, parent, attachToRoot
            )
            viewDataBinding.root
        } catch (e: NullPointerException) {
            LayoutInflater.from(parent.context).inflate(
                layout, parent, attachToRoot
            )
        }
    ) {
        init {
            setIsRecyclable(false)
        }
    }

    override fun getItemCount() = 1
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = viewHolder

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) = Unit

}
