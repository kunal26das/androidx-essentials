package androidx.essentials.playground.ui.fragment

import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentResourcesBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class ResourcesFragment : Fragment() {

    override val layout = R.layout.fragment_resources
    override val viewModel by viewModel<PlayGroundViewModel>()
    override val binding by dataBinding<FragmentResourcesBinding>()

}
