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

    private val locationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it.values.reduce { a, b -> a or b }) {
            binding.location = Location(this)
            binding.executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermission.launch(
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            )
        )
    }

}
