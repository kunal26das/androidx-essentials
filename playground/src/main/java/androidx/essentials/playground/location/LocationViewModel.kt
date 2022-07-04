package androidx.essentials.playground.location

import android.content.SharedPreferences
import androidx.essentials.network.NetworkUnavailableException
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    sharedPreferences: SharedPreferences,
) : ViewModel(), Constant {

    private var job: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    val latitude by sharedPreferences.mutableLiveDataOf<Float>(KEY_LATITUDE)
    val longitude by sharedPreferences.mutableLiveDataOf<Float>(KEY_LONGITUDE)

    fun getLastLocation() {
        job = coroutineScope.launch {
            try {
                val location = locationRepository.getLastLocation()
                latitude.postValue(location?.latitude?.toFloat())
                longitude.postValue(location?.longitude?.toFloat())
            } catch (e: NetworkUnavailableException) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    companion object {
    }

}