package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentNetworkBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class NetworkFragment : Fragment() {

    override val layout = R.layout.fragment_network
    override val viewModel by viewModel<PlayGroundViewModel>()
    override val binding by dataBinding<FragmentNetworkBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.networkStateButton.setOnClickListener {
            viewModel.refreshNetworkState()
        }
    }

}
