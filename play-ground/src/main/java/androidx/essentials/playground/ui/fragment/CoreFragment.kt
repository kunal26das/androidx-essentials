package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.extensions.TryCatch.Try
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentCoreBinding
import androidx.essentials.playground.ui.PlayGroundViewModel
import androidx.navigation.fragment.findNavController

class CoreFragment : Fragment() {

    override val layout = R.layout.fragment_core
    override val binding by dataBinding<FragmentCoreBinding>()
    override val viewModel by viewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.libraries.setOnItemClickListener {
            if (it.itemId != R.id.core) Try {
                findNavController().navigate(it.itemId)
            }
        }
    }


}