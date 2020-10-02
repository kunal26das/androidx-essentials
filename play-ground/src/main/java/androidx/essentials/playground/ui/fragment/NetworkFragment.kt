package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentNetworkBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class NetworkFragment : Fragment() {

    override val layout = R.layout.fragment_network
    private val binding by dataBinding<FragmentNetworkBinding>()
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.networkStateButton.setOnClickListener {
            binding.networkStateButton.text = getNetworkState(
                viewModel.refreshNetworkState()
            )
        }
    }

    override fun initObservers() {
        viewModel.isOnline.observe {
            binding.networkStateButton.text = getNetworkState(it)
        }
    }

    private fun getNetworkState(isOnline: Boolean): String {
        return when (isOnline) {
            true -> getString(R.string.online)
            false -> getString(R.string.offline)
        }
    }

}
