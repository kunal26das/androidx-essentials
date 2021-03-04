package androidx.essentials.playground.ui.auto_complete

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentAutoCompleteBinding

class AutoCompleteFragment : Fragment() {

    override val layout = R.layout.fragment_auto_complete
    override val viewModel by viewModel<AutoCompleteViewModel>()
    override val binding by dataBinding<FragmentAutoCompleteBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        with(binding) {
            validate.setOnClickListener {
                if (autoComplete.isInvalid) {
                    autoComplete.requestFocus()
                } else toast(R.string._true)
            }
        }
    }

}