package androidx.essentials.playground.ui

import android.content.Context
import android.location.Location
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.ViewModel
import androidx.essentials.firebase.Firebase
import androidx.essentials.firebase.utils.UUID
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.R
import androidx.essentials.playground.utils.Listeners
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class PlayGroundViewModel : ViewModel(), Listeners {

    private val firebase by inject<Firebase>()
    private val applicationContext by inject<Context>()
    private val networkCallback by inject<NetworkCallback>()
    private val locationProvider by inject<LocationProvider>()

    val endDate = MutableLiveData<Long>()
    val endTime = MutableLiveData<Long>()
    val startDate = MutableLiveData<Long>()
    val startTime = MutableLiveData<Long>()
    val textInput = MutableLiveData<String>()
    val autoComplete = MutableLiveData<String>()
    val selection = MutableLiveData(emptyArray<String>())

    val isEditable = MutableLiveData(true)
    val isMandatory = MutableLiveData(true)
    val singleSelection = MutableLiveData(true)

    val uuid = MutableLiveData("$UUID")
    val token = MutableLiveData(firebase.token)
    val isOnline = MutableLiveData(networkCallback.isOnline)
    val location = MutableLiveData(locationProvider.location)

    private val libraries = PopupMenu(applicationContext, null).apply {
        MenuInflater(applicationContext).inflate(R.menu.menu_library, menu)
    }.menu.children
    val libraryList = MutableLiveData(libraries.toList())
    val libraryArray = Transformations.map(libraryList) {
        it.map { "${it.title}" }.toTypedArray()
    }

    init {
        firebase.setOnTokenChangeListener(this)
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

    override fun onNewToken(token: String?) {
        this.token.value = token
    }

    override fun onCleared() {
        networkCallback.removeOnNetworkStateChangeListener()
        locationProvider.removeOnLocationChangeListener()
        firebase.removeOnTokenChangeListener()
        super.onCleared()
    }

}