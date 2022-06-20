package androidx.essentials.playground.firebase

import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.essentials.network.Repository
import androidx.essentials.network.local.SharedPreferences
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseService: FirebaseService,
    connectivityManager: ConnectivityManager,
    sharedPreferences: SharedPreferences,
    networkRequest: NetworkRequest,
) : Repository(connectivityManager, networkRequest) {

    val token by sharedPreferences.mutableLiveData<String>(KEY_FCM_TOKEN)

    suspend fun getToken() = execute {
        firebaseService.getToken()?.also {
            token.postValue(it)
        }
    }

    companion object {
        private const val KEY_FCM_TOKEN = "fcm_token"
    }

}