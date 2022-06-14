package androidx.essentials.playground.chips

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentChipsBinding
import androidx.essentials.ui.Fragment
import androidx.fragment.app.viewModels

class ChipsFragment : Fragment() {

    override val layout = R.layout.fragment_chips
    override val viewModel by viewModels<ChipsViewModel>()
    override val binding by dataBinding<FragmentChipsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            chips.setOnHierarchyChangeListener(object : ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View?, child: View?) {
                    if (chips.childCount == viewModel?.libraries?.value?.size) {
                        binding.chips.selection = viewModel?.libraries?.value
                    }
                }

                override fun onChildViewRemoved(parent: View?, child: View?) {
                }

            })
            chips.setOnChipClickListener { _, _, _ ->
                viewModel?.selection?.value = chips.selection
            }
            validate.setOnClickListener {}
        }
    }

}