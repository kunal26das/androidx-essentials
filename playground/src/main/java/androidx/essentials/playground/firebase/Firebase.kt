package androidx.essentials.playground.firebase

import androidx.essentials.preferences.SharedPreferences.get
import androidx.essentials.preferences.SharedPreferences.liveData
import androidx.essentials.preferences.SharedPreferences.put
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

object Firebase {

    val TOKEN by liveData<String>(Preference.FCM_TOKEN)

    var UUID: String
        @Synchronized get() {
            with(get<String>(Preference.UUID)) {
                return when {
                    isNullOrEmpty() -> "${java.util.UUID.randomUUID()}".apply { UUID = this }
                    else -> this
                }
            }
        }
        internal set(value) = put(Preference.UUID, value)

    init {
        Firebase.messaging.token.addOnSuccessListener {
            put(Preference.FCM_TOKEN, it)
        }
    }

    internal enum class Preference {
        FCM_TOKEN, UUID
    }

}