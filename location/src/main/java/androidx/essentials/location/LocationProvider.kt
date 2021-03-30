package androidx.essentials.location

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.get
import androidx.essentials.preferences.SharedPreferences.Companion.put
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationServices

object LocationProvider : SharedPreferences {

    var LOCATION: Pair<Float, Float>? = null
        get() {
            get<Float>(Preference.LATITUDE)?.let { latitude ->
                get<Float>(Preference.LONGITUDE)?.let { longitude ->
                    return Pair(latitude, longitude)
                }
            }
            return null
        }
        internal set(value) {
            field = value?.apply {
                put(Pair(Preference.LATITUDE, first))
                put(Pair(Preference.LONGITUDE, second))
                onLocationChangeListeners.forEach {
                    it.invoke(this)
                }
            }
        }

    val location: LiveData<Pair<Float, Float>> by lazy {
        object : LiveData<Pair<Float, Float>>() {

            val onLocationChangeListener = { location: Pair<Float, Float> ->
                value = location
            }

            init {
                value = LOCATION
            }

            override fun onActive() {
                super.onActive()
                addOnLocationChangeListener(onLocationChangeListener)
            }

            override fun getValue() = LOCATION

            override fun onInactive() {
                removeOnLocationChangeListener(onLocationChangeListener)
                super.onInactive()
            }
        }
    }

    private val onLocationChangeListeners by lazy {
        mutableListOf<(Pair<Float, Float>) -> Unit>()
    }

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
                    it?.apply { LOCATION = Pair(latitude.toFloat(), longitude.toFloat()) }
                }
            }
        }
    }

    fun addOnLocationChangeListener(onLocationChangeListener: (Pair<Float, Float>) -> Unit) {
        onLocationChangeListeners.add(onLocationChangeListener)
    }

    fun removeOnLocationChangeListener(onLocationChangeListener: (Pair<Float, Float>) -> Unit) {
        onLocationChangeListeners.remove(onLocationChangeListener)
    }

    private enum class Preference {
        LATITUDE, LONGITUDE
    }

}
