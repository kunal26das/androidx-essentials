package androidx.essentials.playground.location

import android.content.Context
import androidx.essentials.network.local.SharedPreferences
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Location @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferences(context) {

    val latitude by liveData<Float>(Preference.latitude)
    val longitude by liveData<Float>(Preference.longitude)

    init {
        LocationServices.getFusedLocationProviderClient(context).apply {
            lastLocation.addOnSuccessListener {
                if (it != null) {
                    set(Preference.latitude, it.latitude)
                    set(Preference.longitude, it.longitude)
                }
            }
        }
    }

    @Suppress("EnumEntryName")
    private enum class Preference {
        latitude, longitude
    }

}
