package androidx.essentials.playground.auto_complete

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentAutoCompleteBinding
import androidx.essentials.view.Fragment
import androidx.fragment.app.viewModels

class AutoCompleteFragment : Fragment() {

    override val layout = R.layout.fragment_auto_complete
    private val viewModel by viewModels<AutoCompleteViewModel>()
    override val binding by dataBinding<FragmentAutoCompleteBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        with(binding) {
            validate.setOnClickListener {
                if (autoComplete.isInvalid) {
                    autoComplete.requestFocus()
                }
            }
        }
    }

}