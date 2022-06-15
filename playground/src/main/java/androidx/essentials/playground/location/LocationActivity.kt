package androidx.essentials.playground.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityLocationBinding
import androidx.essentials.view.Activity

class LocationActivity : Activity() {

    override val layout = R.layout.activity_location
    override val binding by dataBinding<ActivityLocationBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.location = Location
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.containsValue(true)) try {
                Location.init(this)
            } catch (ignored: SecurityException) {
            }
        }.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
    }

}
