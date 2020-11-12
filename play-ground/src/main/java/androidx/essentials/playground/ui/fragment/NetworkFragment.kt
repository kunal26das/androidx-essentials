package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentNetworkBinding
import androidx.essentials.playground.ui.PlayGroundViewModel
import androidx.navigation.fragment.findNavController

class NetworkFragment : Fragment<FragmentNetworkBinding>() {

    override val layout = R.layout.fragment_network
    override val sharedViewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sharedViewModel = sharedViewModel
        binding.networkStateButton.setOnClickListener {
            sharedViewModel.refreshNetworkState()
            findNavController().navigate(R.id.io)
        }
    }

}
