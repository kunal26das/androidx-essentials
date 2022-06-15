package androidx.essentials.playground.home

import android.view.ViewGroup
import androidx.essentials.view.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView

class LibraryAdapter : RecyclerView.Adapter<LibraryViewHolder>(),
    OnItemClickListener<Feature> {

    private val libraries = Feature.values()
    override fun getItemCount() = libraries.size
    override var itemClickListener: ((Feature) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = LibraryViewHolder(parent)

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(libraries[position]).also {
            it.binding?.root?.setOnClickListener {
                itemClickListener?.invoke(libraries[position])
            }
        }
    }

}