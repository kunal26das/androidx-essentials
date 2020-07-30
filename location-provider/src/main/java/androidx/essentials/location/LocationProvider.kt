package androidx.essentials.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationProvider private constructor() {

    fun setOnLocationChangeListener(onLocationChangeListener: OnLocationChangeListener) {
        Companion.onLocationChangeListener = onLocationChangeListener
    }

    fun setOnLocationChangeListener(action: (Location?) -> Unit) {
        onLocationChangeListener = object : OnLocationChangeListener {
            override fun onLocationChange(location: Location?) {
                action(location)
            }
        }
    }

    fun removeListener() {
        onLocationChangeListener = null
    }

    interface OnLocationChangeListener {
        fun onLocationChange(location: Location?)
    }

    companion object {

        var LOCATION: Location? = null
            private set(value) {
                field = value
                onLocationChangeListener?.onLocationChange(value)
            }

        private var onLocationChangeListener: OnLocationChangeListener? = null
        private var fusedLocationProviderClient: FusedLocationProviderClient? = null

        @SuppressLint("MissingPermission")
        fun getInstance(context: Context): LocationProvider {
            if (fusedLocationProviderClient != null) {
                return LocationProvider()
            }
            synchronized(this) {
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
                    LOCATION = it
                }
                return LocationProvider()
            }
        }
    }

}
