package androidx.essentials.firebase

import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.get
import androidx.essentials.preferences.SharedPreferences.Companion.liveData
import androidx.essentials.preferences.SharedPreferences.Companion.put
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

object Firebase : SharedPreferences {

    val TOKEN by liveData<String>(Preference.TOKEN)

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
            put(Preference.TOKEN, it)
        }
    }

    internal enum class Preference {
        TOKEN, UUID
    }

}