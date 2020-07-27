package androidx.essentials.playground

import androidx.essentials.core.KoinComponent.inject
import androidx.essentials.core.ViewModel
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel() {

    private val networkCallback: NetworkCallback by inject()
    private val locationProvider: LocationProvider by inject()

    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)

    init {
        setOnLocationChangeListener()
        setOnNetworkStateChangeListener()
    }

    private fun setOnNetworkStateChangeListener() {
        networkCallback.setOnNetworkStateChangeListener {
            isOnline.postValue(it)
        }
    }

    private fun setOnLocationChangeListener() {
        locationProvider.setOnLocationChangeListener {
            location.postValue(it)
        }
    }

    override fun onCleared() {
        locationProvider.removeListener()
        networkCallback.removeListener()
        super.onCleared()
    }

}