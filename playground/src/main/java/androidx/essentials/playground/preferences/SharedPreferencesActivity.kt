package androidx.essentials.playground.preferences

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivitySharedPreferencesBinding
import androidx.essentials.view.Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedPreferencesActivity : Activity() {

    override val layout = R.layout.activity_shared_preferences
    private val viewModel by viewModels<SharedPreferencesViewModel>()
    override val binding by dataBinding<ActivitySharedPreferencesBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}