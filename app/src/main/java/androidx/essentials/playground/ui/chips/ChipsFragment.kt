package androidx.essentials.playground.ui.chips

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
            chips.setOnHierarchyChangeListener(object : ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View?, child: View?) {
                    if (chips.childCount == viewModel?.libraries?.value?.size) {
                        binding.chips.selection = viewModel?.libraries?.value
                    }
                }

                override fun onChildViewRemoved(parent: View?, child: View?) {
                }

            })
            chips.setOnChipClickListener { index, item ->
                viewModel?.selection?.value = chips.selection
            }
            validate.setOnClickListener {
                toast("${chips.isValid}")
            }
        }
    }

}