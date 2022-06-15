package androidx.essentials.playground.backdrop

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityBackdropBinding
import androidx.essentials.view.Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BackdropActivity : Activity() {

    override val layout = R.layout.activity_backdrop
    private val viewModel by viewModels<BackdropViewModel>()
    override val binding by dataBinding<ActivityBackdropBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}