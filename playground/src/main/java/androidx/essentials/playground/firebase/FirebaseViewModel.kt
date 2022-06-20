package androidx.essentials.playground.firebase

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
) : ViewModel() {

    private var job: Job? = null
    val token = firebaseRepository.token
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun getToken() {
        job = coroutineScope.launch {
            firebaseRepository.getToken()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}