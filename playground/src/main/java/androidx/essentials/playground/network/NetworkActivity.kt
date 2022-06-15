package androidx.essentials.playground.network

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityNetworkBinding
import androidx.essentials.view.Activity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkActivity : Activity() {

    @Inject
    lateinit var network: Network
    override val layout = R.layout.activity_network
    override val binding by dataBinding<ActivityNetworkBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.network = network
    }

}
