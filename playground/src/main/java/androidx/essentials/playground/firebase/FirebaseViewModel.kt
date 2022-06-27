package androidx.essentials.playground.firebase

import android.content.SharedPreferences
import androidx.essentials.network.NetworkUnavailableException
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    sharedPreferences: SharedPreferences,
) : ViewModel() {

    private var job: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    val token by sharedPreferences.mutableLiveDataOf<String>(KEY_FCM_TOKEN)

    fun getToken() {
        job = coroutineScope.launch {
            try {
                token.postValue(firebaseRepository.getToken())
            } catch (e: NetworkUnavailableException) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    companion object {
        private const val KEY_FCM_TOKEN = "fcm_token"
    }

}