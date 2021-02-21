package androidx.essentials.playground.ui.shared_preferences

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentSharedPreferencesBinding

class SharedPreferencesFragment : Fragment() {

    override val layout = R.layout.fragment_shared_preferences
    override val viewModel by viewModel<SharedPreferencesViewModel>()
    override val binding by dataBinding<FragmentSharedPreferencesBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}