package androidx.essentials.playground.network

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    val maxTimeToLive = networkRepository.maxTimeToLive
    val isNetworkBlocked = networkRepository.isNetworkBlocked
    val isNetworkAvailable = networkRepository.isNetworkAvailable
    val networkCapabilities = networkRepository.networkCapabilities
    val networkLinkProperties = networkRepository.networkLinkProperties

    override fun onCleared() {
        networkRepository.close()
        super.onCleared()
    }

}