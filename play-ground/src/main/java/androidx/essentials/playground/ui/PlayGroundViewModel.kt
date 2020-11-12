package androidx.essentials.playground.ui

import android.location.Location
import android.view.MenuItem
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.ViewModel
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.utils.Listeners
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel(), Listeners {

    private val networkCallback by inject<NetworkCallback>()
    private val locationProvider by inject<LocationProvider>()

    val endDate = MutableLiveData<Long>()
    val startDate = MutableLiveData<Long>()
    val textInput = MutableLiveData<String>()
    val autoComplete = MutableLiveData<String>()
    val libraries = MutableLiveData<List<MenuItem>>()
    val selection = MutableLiveData(emptyArray<String>())

    val isEditable = MutableLiveData(true)
    val isMandatory = MutableLiveData(true)
    val singleSelection = MutableLiveData(true)

    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)

    init {
        locationProvider.setOnLocationChangeListener(this)
        networkCallback.setOnNetworkStateChangeListener(this)
    }

    fun refreshNetworkState() = networkCallback.refreshNetworkState()

    override fun onLocationChange(location: Location?) {
        this.location.postValue(location)
    }

    override fun onNetworkStateChange(isOnline: Boolean) {
        this.isOnline.postValue(isOnline)
    }

    override fun onCleared() {
        networkCallback.removeOnNetworkStateChangeListener()
        locationProvider.removeOnLocationChangeListener()
        super.onCleared()
    }

}