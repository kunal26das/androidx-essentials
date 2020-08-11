package androidx.essentials.playground.ui

import android.location.Location
import androidx.essentials.core.KoinComponent.inject
import androidx.essentials.core.ViewModel
import androidx.essentials.firebase.Firebase
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.utils.Listeners
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference

class PlayGroundViewModel : ViewModel(), Listeners {

    private val firebase: Firebase by inject()
    val database: DatabaseReference by inject()
    private val networkCallback: NetworkCallback by inject()
    private val locationProvider: LocationProvider by inject()

    val token = MutableLiveData(Firebase.TOKEN)
    val isEditable = MutableLiveData(true)
    val isMandatory = MutableLiveData(false)
    val isOnline = MutableLiveData(NetworkCallback.IS_ONLINE)
    val location = MutableLiveData(LocationProvider.LOCATION)

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