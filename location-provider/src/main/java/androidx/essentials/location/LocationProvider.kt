package androidx.essentials.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationProvider private constructor() {

    private var onLocationChangeListener: OnLocationChangeListener? = null

    init {
        init()
    }

    @SuppressLint("MissingPermission")
    private fun init() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
            onLocationChangeListener?.onLocationChange(it.latitude, it.longitude)
        }
    }

    fun setOnLocationChangeListener(onLocationChangeListener: OnLocationChangeListener) {
        this.onLocationChangeListener = onLocationChangeListener
    }

    fun setOnLocationChangeListener(action: (latitude: Double, longitude: Double) -> Unit) {
        onLocationChangeListener = object : OnLocationChangeListener {
            override fun onLocationChange(latitude: Double, longitude: Double) {
                action.invoke(latitude, longitude)
            }
        }

    }

    interface OnLocationChangeListener {
        fun onLocationChange(latitude: Double, longitude: Double)
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
