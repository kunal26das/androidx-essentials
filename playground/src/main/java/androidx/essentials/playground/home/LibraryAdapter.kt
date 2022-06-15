package androidx.essentials.playground.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LibraryAdapter : RecyclerView.Adapter<LibraryViewHolder>() {

    private val libraries = Library.values()

    override fun getItemCount() = libraries.size

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = LibraryViewHolder(parent)

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(libraries[position])
    }
}