package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.ui.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel
import kotlinx.android.synthetic.main.fragment_network.*

class NetworkFragment : Fragment(true) {

    override val layout = R.layout.fragment_network
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkStateButton.setOnClickListener {
            networkStateButton.text = getNetworkState(
                viewModel.refreshNetworkState()
            )
        }
    }

    override fun initObservers() {
        viewModel.isOnline.observe {
            networkStateButton.text = getNetworkState(it)
        }
    }

    private fun getNetworkState(isOnline: Boolean): String {
        return when (isOnline) {
            true -> getString(R.string.online)
            false -> getString(R.string.offline)
        }
    }

}
