package androidx.essentials.playground.autocomplete

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityAutoCompleteBinding
import androidx.essentials.view.Activity

class AutoCompleteActivity : Activity() {

    override val layout = R.layout.activity_auto_complete
    private val viewModel by viewModels<AutoCompleteViewModel>()
    override val binding by dataBinding<ActivityAutoCompleteBinding>()

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