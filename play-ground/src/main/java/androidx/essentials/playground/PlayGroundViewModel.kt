package androidx.essentials.playground

import androidx.essentials.core.KoinComponent.inject
import androidx.essentials.core.ViewModel
import androidx.essentials.firebase.Firebase
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel() {

    private val firebase: Firebase by inject()
    private val networkCallback: NetworkCallback by inject()
    private val locationProvider: LocationProvider by inject()

    val token = MutableLiveData(Firebase.TOKEN)
    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)

    init {
        setOnTokenChangeListener()
        setOnLocationChangeListener()
        setOnNetworkStateChangeListener()
    }

    private fun setOnTokenChangeListener() {
        firebase.setOnTokenChangeListener {
            token.postValue(it)
        }
    }

    private fun setOnLocationChangeListener() {
        locationProvider.setOnLocationChangeListener {
            location.postValue(it)
        }
    }

    private fun setOnNetworkStateChangeListener() {
        networkCallback.setOnNetworkStateChangeListener {
            isOnline.postValue(it)
        }
    }

    override fun onCleared() {
        locationProvider.removeListener()
        networkCallback.removeListener()
        firebase.removeListener()
        super.onCleared()
    }

}