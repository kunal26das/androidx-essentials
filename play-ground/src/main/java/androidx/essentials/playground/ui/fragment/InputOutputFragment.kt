package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.children
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentInputOutputBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class InputOutputFragment : Fragment() {

    override val layout = R.layout.fragment_input_output
    override val viewModel by sharedViewModel<PlayGroundViewModel>()
    private val binding by dataBinding<FragmentInputOutputBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            PopupMenu(context, null).apply {
                MenuInflater(context).inflate(R.menu.menu_play_ground, menu)
                menu.children.map {
                    "${it.title}"
                }.toList().toTypedArray().let {
                    autoComplete.array = it
                    chips.array = it
                }
            }
            validate.setOnClickListener {
                Toast.makeText(
                    requireContext(), "${
                        true
                            and textInput.isValid
                            and autoComplete.isValid
                            and startDate.isValid
                            and endDate.isValid
                            and chips.isValid
                    }", Toast.LENGTH_SHORT
                ).show()
            }
            autoComplete.setOnItemClickListener { index, item ->
                Toast.makeText(requireContext(), "$index: $item", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
