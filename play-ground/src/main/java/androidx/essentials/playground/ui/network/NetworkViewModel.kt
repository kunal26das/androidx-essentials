package androidx.essentials.playground.ui.network

import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.network.NetworkCallback
import androidx.lifecycle.MutableLiveData

class NetworkViewModel : ViewModel(), NetworkCallback.OnNetworkStateChangeListener {

    private val networkCallback by inject<NetworkCallback>()
    val isOnline = MutableLiveData(networkCallback.isOnline)

    init {
        networkCallback.setOnNetworkStateChangeListener(this)
    }

    fun refreshNetworkState() = networkCallback.refreshNetworkState()

    override fun onNetworkStateChange(isOnline: Boolean) {
        this.isOnline.postValue(isOnline)
    }

    override fun onCleared() {
        networkCallback.removeOnNetworkStateChangeListener()
        super.onCleared()
    }

}