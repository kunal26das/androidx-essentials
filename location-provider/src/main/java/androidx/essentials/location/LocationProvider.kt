package androidx.essentials.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationProvider {

    @SuppressLint("MissingPermission")
    fun setOnLocationChangeListener(action: (latitude: Double, longitude: Double) -> Unit) {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
            action.invoke(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
        }
    }

    companion object {
        private var fusedLocationProviderClient: FusedLocationProviderClient? = null
        fun getInstance(context: Context): LocationProvider {
            if (fusedLocationProviderClient != null) {
                return LocationProvider()
            }
            synchronized(this) {
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                return LocationProvider()
            }
        }
    }

}
