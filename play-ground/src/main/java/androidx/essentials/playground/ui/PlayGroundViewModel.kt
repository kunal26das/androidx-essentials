package androidx.essentials.playground.ui

import android.location.Location
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.mvvm.ViewModel
import androidx.essentials.firebase.Firebase
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.utils.Listeners
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel(), Listeners {

    private val firebase: Firebase by inject()
    private val networkCallback: NetworkCallback by inject()
    private val locationProvider: LocationProvider by inject()

    val date = MutableLiveData<Long>()
    val textInput = MutableLiveData<String>()
    val autoComplete = MutableLiveData<String>()
    val token = MutableLiveData(firebase.token)
    val isEditable = MutableLiveData(true)
    val isMandatory = MutableLiveData(true)
    val singleSelection = MutableLiveData(true)
    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)
    val selection = MutableLiveData(emptyArray<String>())

    init {
        firebase.setOnTokenChangeListener(this)
        locationProvider.setOnLocationChangeListener(this)
        networkCallback.setOnNetworkStateChangeListener(this)
    }

    fun refreshNetworkState() = networkCallback.refreshNetworkState()

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
        networkCallback.removeOnNetworkStateChangeListener()
        locationProvider.removeOnLocationChangeListener()
        firebase.removeOnTokenChangeListener()
        super.onCleared()
    }

}