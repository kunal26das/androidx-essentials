package androidx.essentials.playground.ui.home

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override val layout = R.layout.fragment_home
    override val viewModel by viewModel<HomeViewModel>()
    override val binding by dataBinding<FragmentHomeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}