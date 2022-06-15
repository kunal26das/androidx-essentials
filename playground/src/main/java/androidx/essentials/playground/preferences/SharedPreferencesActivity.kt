package androidx.essentials.playground.preferences

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivitySharedPreferencesBinding
import androidx.essentials.view.Fragment
import androidx.fragment.app.viewModels

class SharedPreferencesActivity : Fragment() {

    override val layout = R.layout.activity_shared_preferences
    private val viewModel by viewModels<SharedPreferencesViewModel>()
    override val binding by dataBinding<ActivitySharedPreferencesBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}