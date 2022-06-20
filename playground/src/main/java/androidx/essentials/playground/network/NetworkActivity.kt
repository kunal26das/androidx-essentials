package androidx.essentials.playground.network

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityNetworkBinding
import androidx.essentials.view.Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkActivity : Activity() {

    override val layout = R.layout.activity_network
    private val viewModel by viewModels<NetworkViewModel>()
    override val binding by dataBinding<ActivityNetworkBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}
