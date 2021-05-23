package androidx.essentials.playground.network

import android.os.Bundle
import android.view.View
import androidx.essentials.fragment.Fragment
import androidx.essentials.network.Network
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentNetworkBinding

class NetworkFragment : Fragment() {

    override val layout = R.layout.fragment_network
    override val binding by dataBinding<FragmentNetworkBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.network = Network
    }

}
