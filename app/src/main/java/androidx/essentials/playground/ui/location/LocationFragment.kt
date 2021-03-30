package androidx.essentials.playground.ui.location

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.location.LocationProvider
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    override val layout = R.layout.fragment_location
    override val binding by dataBinding<FragmentLocationBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationProvider = LocationProvider
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.containsValue(true)) try {
                LocationProvider.init(requireContext())
            } catch (ignored: SecurityException) {
            }
        }.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

}
