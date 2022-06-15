package androidx.essentials.playground.firebase

import android.content.Context
import androidx.essentials.network.local.SharedPreferences
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Firebase @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferences(context) {

    val token by liveData<String>(KEY_FCM_TOKEN)

    var uuid: String
        @Synchronized get() {
            with(get<String>(KEY_UUID)) {
                return when {
                    isNullOrEmpty() -> "${java.util.UUID.randomUUID()}".apply { uuid = this }
                    else -> this
                }
            }
        }
        internal set(value) = set(KEY_UUID, value)

    init {
        Firebase.messaging.token.addOnSuccessListener {
            set(KEY_FCM_TOKEN, it)
        }
    }

    companion object {
        private const val KEY_UUID = "uuid"
        private const val KEY_FCM_TOKEN = "fcm_token"
    }

}