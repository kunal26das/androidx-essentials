package androidx.essentials.location

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.liveData
import androidx.essentials.preferences.SharedPreferences.Companion.put
import com.google.android.gms.location.LocationServices

object Location : SharedPreferences {

    val LATITUDE by liveData<Float>(Preference.LATITUDE)
    val LONGITUDE by liveData<Float>(Preference.LONGITUDE)

    @Synchronized
    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ]
    )
    fun init(context: Context) {
        synchronized(this) {
            LocationServices.getFusedLocationProviderClient(context).apply {
                lastLocation.addOnSuccessListener {
                    if (it != null) {
                        put(Preference.LATITUDE, it.latitude)
                        put(Preference.LONGITUDE, it.longitude)
                    }
                }
            }
        }
    }

    private enum class Preference {
        LATITUDE, LONGITUDE
    }

}
