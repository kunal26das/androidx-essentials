package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentCoreBinding
import androidx.essentials.playground.ui.PlayGroundViewModel
import androidx.navigation.fragment.findNavController

class CoreFragment : Fragment<FragmentCoreBinding>() {

    override val layout = R.layout.fragment_core
    override val sharedViewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sharedViewModel = sharedViewModel
        binding.libraryList.setOnItemClickListener {
            if (it.itemId != R.id.core) try {
                findNavController().navigate(it.itemId)
            } catch (e: IllegalArgumentException) {

            }
        }
    }


}