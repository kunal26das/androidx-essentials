package androidx.essentials.playground.backdrop

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentBackdropBinding
import androidx.essentials.ui.Fragment
import androidx.fragment.app.viewModels

class BackdropFragment : Fragment() {

    override val layout = R.layout.fragment_backdrop
    override val viewModel by viewModels<BackdropViewModel>()
    override val binding by dataBinding<FragmentBackdropBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}