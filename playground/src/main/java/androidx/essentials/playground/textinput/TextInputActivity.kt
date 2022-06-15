package androidx.essentials.playground.textinput

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityTextInputBinding
import androidx.essentials.view.Fragment
import androidx.fragment.app.viewModels

class TextInputActivity : Fragment() {

    override val layout = R.layout.activity_text_input
    private val viewModel by viewModels<TextInputViewModel>()
    override val binding by dataBinding<ActivityTextInputBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        with(binding) {
            validate.setOnClickListener {
                if (textInput.isInvalid) {
                    textInput.requestFocus()
                }
            }
        }
    }

}