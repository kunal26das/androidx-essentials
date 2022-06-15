package androidx.essentials.playground.chips

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentChipsBinding
import androidx.essentials.view.Fragment
import androidx.fragment.app.viewModels

class ChipsFragment : Fragment() {

    override val layout = R.layout.fragment_chips
    private val viewModel by viewModels<ChipsViewModel>()
    override val binding by dataBinding<FragmentChipsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.chips.setOnChipClickListener { _, _, _ ->
            viewModel.selection.value = binding.chips.selection
        }
    }

}