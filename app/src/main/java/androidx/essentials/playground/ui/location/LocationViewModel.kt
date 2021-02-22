package androidx.essentials.playground.ui.location

import android.location.Location
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.location.LocationProvider
import androidx.lifecycle.MutableLiveData

class LocationViewModel : ViewModel(), LocationProvider.OnLocationChangeListener {

    private val locationProvider by inject<LocationProvider>()
    val location = MutableLiveData(locationProvider.location)

    init {
        locationProvider.setOnLocationChangeListener(this)
    }

    override fun onLocationChange(location: Location?) {
        location?.let { this.location.postValue(it) }
    }

    override fun onCleared() {
        locationProvider.removeOnLocationChangeListener()
        super.onCleared()
    }

}