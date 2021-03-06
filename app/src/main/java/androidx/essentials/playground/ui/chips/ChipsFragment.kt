package androidx.essentials.playground.ui.chips

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentChipsBinding

class ChipsFragment : Fragment() {

    override val layout = R.layout.fragment_chips
    override val viewModel by viewModel<ChipsViewModel>()
    override val binding by dataBinding<FragmentChipsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            validate.setOnClickListener {
                if (chips.isInvalid) {
                    chips.requestFocus()
                } else toast(R.string._true)
            }
        }
    }

}