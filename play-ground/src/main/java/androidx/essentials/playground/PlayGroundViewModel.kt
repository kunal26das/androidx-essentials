package androidx.essentials.playground

import androidx.essentials.core.KoinComponent.inject
import androidx.essentials.core.ViewModel
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel() {

    private val networkCallback: NetworkCallback by inject()
    private val locationProvider: LocationProvider by inject()

    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()
    val isOnline = MutableLiveData<Boolean>()

    init {
        setOnLocationChangeListener()
        setOnNetworkStateChangeListener()
    }

    private fun setOnNetworkStateChangeListener() {
        networkCallback.setOnNetworkStateChangeListener { isOnline ->
            this.isOnline.postValue(isOnline)
        }
    }

    private fun setOnLocationChangeListener() {
        locationProvider.setOnLocationChangeListener { latitude, longitude ->
            this.latitude.postValue(latitude)
            this.longitude.postValue(longitude)
        }
    }

    override fun onCleared() {
        locationProvider.removeListener()
        networkCallback.removeListener()
        super.onCleared()
    }

}