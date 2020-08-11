package androidx.essentials.playground.ui.fragment

import androidx.essentials.core.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel

class ResourcesFragment : Fragment(true) {

    override val layout = R.layout.fragment_resources
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

}
