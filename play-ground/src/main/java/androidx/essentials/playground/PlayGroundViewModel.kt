package androidx.essentials.playground

import android.location.Location
import androidx.essentials.core.KoinComponent.inject
import androidx.essentials.core.ViewModel
import androidx.essentials.extensions.Transformations
import androidx.essentials.firebase.Firebase
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel(), Listeners {

    private val firebase: Firebase by inject()
    private val networkCallback: NetworkCallback by inject()
    private val locationProvider: LocationProvider by inject()

    val token = MutableLiveData(firebase.token)
    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)

    val combined = Transformations.switchMap<List<Any?>>(token, isOnline, location) {
        MutableLiveData(it)
    }

    init {
        firebase.setOnTokenChangeListener(this)
        locationProvider.setOnLocationChangeListener(this)
        networkCallback.setOnNetworkStateChangeListener(this)
    }

    override fun onNewToken(token: String?) {
        this.token.postValue(token)
    }

    override fun onLocationChange(location: Location?) {
        this.location.postValue(location)
    }

    override fun onNetworkStateChange(isOnline: Boolean) {
        this.isOnline.postValue(isOnline)
    }

    override fun onCleared() {
        locationProvider.removeListener()
        networkCallback.removeListener()
        firebase.removeListener()
        super.onCleared()
    }

}