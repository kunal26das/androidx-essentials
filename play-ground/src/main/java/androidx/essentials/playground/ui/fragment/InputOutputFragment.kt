package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        binding.apply {
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
        }
    }

}
