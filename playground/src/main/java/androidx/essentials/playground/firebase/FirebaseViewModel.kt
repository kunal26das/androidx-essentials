package androidx.essentials.playground.firebase

import androidx.essentials.network.local.SharedPreferences
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
    private val default = CoroutineScope(Dispatchers.Default)

    val token by sharedPreferences.mutableLiveData<String>("token")

    fun getToken() {
        default.launch {
            val result = firebaseRepository.getToken()
            token.postValue(result)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}