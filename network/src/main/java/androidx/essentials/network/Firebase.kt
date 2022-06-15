package androidx.essentials.network

import androidx.essentials.network.local.SharedPreferences.get
import androidx.essentials.network.local.SharedPreferences.liveData
import androidx.essentials.network.local.SharedPreferences.put
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

object Firebase {

    val TOKEN by liveData<String>(Preference.fcm_token)

    var UUID: String
        @Synchronized get() {
            with(get<String>(Preference.uuid)) {
                return when {
                    isNullOrEmpty() -> "${java.util.UUID.randomUUID()}".apply { UUID = this }
                    else -> this
                }
            }
        }
        internal set(value) = put(Preference.uuid, value)

    init {
        Firebase.messaging.token.addOnSuccessListener {
            put(Preference.fcm_token, it)
        }
    }

    @Suppress("EnumEntryName")
    internal enum class Preference {
        fcm_token, uuid
    }

}