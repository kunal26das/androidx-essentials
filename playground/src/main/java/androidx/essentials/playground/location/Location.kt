package androidx.essentials.playground.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.essentials.network.local.SharedPreferences.liveData
import androidx.essentials.network.local.SharedPreferences.put
import com.google.android.gms.location.LocationServices

object Location {

    val LATITUDE by liveData<Float>(Preference.latitude)
    val LONGITUDE by liveData<Float>(Preference.longitude)

    @Synchronized
    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    fun init(context: Context) {
        synchronized(this) {
            LocationServices.getFusedLocationProviderClient(context).apply {
                lastLocation.addOnSuccessListener {
                    if (it != null) {
                        put(Preference.latitude, it.latitude)
                        put(Preference.longitude, it.longitude)
                    }
                }
            }
        }
    }

    @Suppress("EnumEntryName")
    private enum class Preference {
        latitude, longitude
    }

}
