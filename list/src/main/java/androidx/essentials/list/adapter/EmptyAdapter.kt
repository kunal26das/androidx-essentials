package androidx.essentials.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EmptyAdapter(
    private val layout: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = 1

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layout, parent, false
            )
        ) {}
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) = Unit

}
