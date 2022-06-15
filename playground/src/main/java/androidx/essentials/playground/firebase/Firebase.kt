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

    val token by liveData<String>(Preference.fcm_token)

    var uuid: String
        @Synchronized get() {
            with(get<String>(Preference.uuid)) {
                return when {
                    isNullOrEmpty() -> "${java.util.UUID.randomUUID()}".apply { uuid = this }
                    else -> this
                }
            }
        }
        internal set(value) = set(Preference.uuid, value)

    init {
        Firebase.messaging.token.addOnSuccessListener {
            set(Preference.fcm_token, it)
        }
    }

    @Suppress("EnumEntryName")
    internal enum class Preference {
        fcm_token, uuid
    }

}