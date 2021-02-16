package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentCoreBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class CoreFragment : Fragment() {

    override val layout = R.layout.fragment_core
    override val binding by dataBinding<FragmentCoreBinding>()
    override val viewModel by viewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}