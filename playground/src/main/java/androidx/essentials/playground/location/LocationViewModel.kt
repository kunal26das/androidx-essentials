package androidx.essentials.playground.location

import androidx.essentials.network.local.SharedPreferences
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
) : ViewModel() {

    private var job: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    val latitude by sharedPreferences.mutableLiveData<Float>(KEY_LATITUDE)
    val longitude by sharedPreferences.mutableLiveData<Float>(KEY_LONGITUDE)

    fun getLastLocation() {
        job = coroutineScope.launch {
            val location = locationRepository.getLastLocation()
            latitude.postValue(location?.latitude?.toFloat())
            longitude.postValue(location?.longitude?.toFloat())
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    companion object {
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

}