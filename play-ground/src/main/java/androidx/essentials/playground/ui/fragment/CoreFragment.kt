package androidx.essentials.playground.ui.fragment

import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.extensions.Try.Try
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentCoreBinding
import androidx.essentials.playground.ui.PlayGroundViewModel
import androidx.navigation.fragment.findNavController

class CoreFragment : Fragment() {

    override val layout = R.layout.fragment_core
    private val binding by dataBinding<FragmentCoreBinding>()
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onResume() {
        super.onResume()
        PopupMenu(context, null).apply {
            MenuInflater(context).inflate(R.menu.menu_play_ground, menu)
            binding.libraryList.submitList(menu.children.toList())
            binding.libraryList.setOnItemClickListener {
                if (it.itemId != R.id.core) Try {
                    findNavController().navigate(it.itemId)
                }
            }
        }
    }

}