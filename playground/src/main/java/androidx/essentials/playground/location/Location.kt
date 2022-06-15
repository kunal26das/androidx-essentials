package androidx.essentials.playground.location

import android.content.Context
import androidx.essentials.network.local.SharedPreferences
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Location @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferences(context) {

    val latitude by liveData<Float>(KEY_LATITUDE)
    val longitude by liveData<Float>(KEY_LONGITUDE)

    init {
        LocationServices.getFusedLocationProviderClient(context).apply {
            lastLocation.addOnSuccessListener {
                if (it != null) {
                    set(KEY_LATITUDE, it.latitude)
                    set(KEY_LONGITUDE, it.longitude)
                }
            }
        }
    }

    companion object {
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

}
