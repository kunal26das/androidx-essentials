package androidx.essentials.playground.ui.fragment

import androidx.essentials.core.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel
import kotlinx.android.synthetic.main.fragment_network.*

class NetworkFragment : Fragment(true) {

    override val layout = R.layout.fragment_network
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun initObservers() {
        viewModel.isOnline.observe {
            networkStateTextView.text = when (it) {
                true -> getString(R.string.online)
                false -> getString(R.string.offline)
            }
        }
    }

}
