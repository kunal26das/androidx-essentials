package androidx.essentials.playground.ui.fragment

import androidx.essentials.core.ui.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel

class BackdropFragment : Fragment() {

    override val layout = R.layout.fragment_backdrop
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

}