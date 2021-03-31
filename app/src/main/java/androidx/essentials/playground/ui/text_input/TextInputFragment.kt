package androidx.essentials.playground.ui.text_input

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentTextInputBinding
import androidx.fragment.app.viewModels

class TextInputFragment : Fragment() {

    override val layout = R.layout.fragment_text_input
    override val viewModel by viewModels<TextInputViewModel>()
    override val binding by dataBinding<FragmentTextInputBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        with(binding) {
            validate.setOnClickListener {
                if (textInput.isInvalid) {
                    textInput.requestFocus()
                } else toast(R.string._true)
            }
        }
    }

}