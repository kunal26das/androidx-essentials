package androidx.essentials.playground.chips

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityChipsBinding
import androidx.essentials.view.Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChipsActivity : Activity() {

    override val layout = R.layout.activity_chips
    private val viewModel by viewModels<ChipsViewModel>()
    override val binding by dataBinding<ActivityChipsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.chips.setOnChipClickListener { _, _, _ ->
            viewModel.selection.value = binding.chips.selection
        }
    }

}