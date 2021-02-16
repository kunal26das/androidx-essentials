package androidx.essentials.playground.ui.fragment

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentLocationBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class LocationFragment : Fragment() {

    override val layout = R.layout.fragment_location
    override val viewModel by viewModel<PlayGroundViewModel>()
    override val binding by dataBinding<FragmentLocationBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE_LOCATION
        )
    }

    companion object {
        private const val REQUEST_CODE_LOCATION = 0
    }

}
