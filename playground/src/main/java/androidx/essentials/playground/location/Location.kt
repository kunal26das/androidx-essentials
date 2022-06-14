package androidx.essentials.playground.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.essentials.network.SharedPreferences.liveData
import androidx.essentials.network.SharedPreferences.put
import com.google.android.gms.location.LocationServices

object Location {

    val LATITUDE by liveData<Float>(Preference.LATITUDE)
    val LONGITUDE by liveData<Float>(Preference.LONGITUDE)

    @Synchronized
    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
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
