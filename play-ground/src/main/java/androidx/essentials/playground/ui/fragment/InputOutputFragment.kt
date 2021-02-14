package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentInputOutputBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class InputOutputFragment : Fragment() {

    override val layout = R.layout.fragment_input_output
    override val viewModel by viewModel<PlayGroundViewModel>()
    override val binding by dataBinding<FragmentInputOutputBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            textInput.setOnCutListener { toast("$it") }
            textInput.setOnCopyListener { toast("$it") }
            textInput.setOnPasteListener { toast("$it") }
            validate.setOnClickListener {
                toast(
                    "${
                        true
                            and textInput.isValid
                            and autoComplete.isValid
                            and startDate.isValid
                            and endDate.isValid
                            and startTime.isValid
                            and endTime.isValid
                            and chips.isValid
                    }"
                )
            }
        }
    }

}
