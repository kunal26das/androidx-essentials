package androidx.essentials.playground.ui.fragment

import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentBackdropBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class BackdropFragment : Fragment<FragmentBackdropBinding>() {

    override val layout = R.layout.fragment_backdrop
    override val sharedViewModel by sharedViewModel<PlayGroundViewModel>()

}