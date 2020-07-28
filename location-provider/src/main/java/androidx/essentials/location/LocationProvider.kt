package androidx.essentials.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationProvider private constructor() {

    var location: Location? = null
        set(value) {
            field = value
            onLocationChangeListener?.onLocationChange(value)
        }
    private var onLocationChangeListener: OnLocationChangeListener? = null

    init {
        init()
    }

    @SuppressLint("MissingPermission")
    private fun init() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
            location = it
        }
    }

    fun setOnLocationChangeListener(onLocationChangeListener: OnLocationChangeListener) {
        this.onLocationChangeListener = onLocationChangeListener
    }

    fun setOnLocationChangeListener(action: (location: Location?) -> Unit) {
        onLocationChangeListener = object : OnLocationChangeListener {
            override fun onLocationChange(location: Location?) {
                action(location)
            }
        }
    }

    fun removeListener() {
        this.onLocationChangeListener = null
    }

    interface OnLocationChangeListener {
        fun onLocationChange(location: Location?)
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
