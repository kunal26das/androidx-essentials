package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentInputOutputBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class InputOutputFragment : Fragment<FragmentInputOutputBinding>() {

    override val layout = R.layout.fragment_input_output
    override val sharedViewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sharedViewModel = sharedViewModel
        binding.textInput.setOnCutListener { toast("$it") }
        binding.textInput.setOnCopyListener { toast("$it") }
        binding.textInput.setOnPasteListener { toast("$it") }
        binding.apply {
            validate.setOnClickListener {
                toast(
                    "${
                        true
                            and textInput.isValid
                            and autoComplete.isValid
                            and startDate.isValid
                            and endDate.isValid
                            and chips.isValid
                    }"
                )
            }
        }
    }

}
