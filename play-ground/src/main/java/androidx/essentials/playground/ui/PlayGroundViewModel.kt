package androidx.essentials.playground.ui

import android.content.Context
import android.location.Location
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.ViewModel
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.R
import androidx.essentials.playground.utils.Listeners
import androidx.lifecycle.MutableLiveData

class PlayGroundViewModel : ViewModel(), Listeners {

    private val applicationContext by inject<Context>()
    private val networkCallback by inject<NetworkCallback>()
    private val locationProvider by inject<LocationProvider>()

    val date = MutableLiveData<Long>()
    val textInput = MutableLiveData<String>()
    val autoComplete = MutableLiveData<String>()
    val libraries = MutableLiveData<List<MenuItem>>()

    val isEditable = MutableLiveData(true)
    val isMandatory = MutableLiveData(true)
    val singleSelection = MutableLiveData(true)
    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)
    val selection = MutableLiveData(emptyArray<String>())

    init {
        locationProvider.setOnLocationChangeListener(this)
        networkCallback.setOnNetworkStateChangeListener(this)
        PopupMenu(applicationContext, null).apply {
            MenuInflater(applicationContext).inflate(R.menu.menu_play_ground, menu)
            libraries.value = menu.children.toList()
        }
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