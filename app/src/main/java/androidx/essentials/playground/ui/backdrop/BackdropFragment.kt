package androidx.essentials.playground.ui.backdrop

import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentBackdropBinding

class BackdropFragment : Fragment() {

    override val layout = R.layout.fragment_backdrop
    override val binding by dataBinding<FragmentBackdropBinding>()

}